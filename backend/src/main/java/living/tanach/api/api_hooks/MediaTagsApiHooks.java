package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.api_hooks.ApiHooks;
import dev.sanda.datafi.dto.FreeTextSearchPageRequest;
import dev.sanda.datafi.service.DataManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import living.tanach.api.model.entities.MediaTag;
import living.tanach.api.model.entities.Verse;
import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MediaTagsApiHooks implements ApiHooks<MediaTag> {

  private final S3Service s3Service;
  private final VerseFreeTextSearchEngine searchEngine;
  private final DataManager<Verse> verseDataManager;
  private static final Executor executor = Executors.newFixedThreadPool(100);

  @Override
  public void postGetBatchByIds(
    List<MediaTag> result,
    DataManager<MediaTag> dataManager
  ) {
    List<Runnable> tasks = new ArrayList<>();
    result.forEach(
      mediaTag ->
        mediaTag
          .getLinkedContent()
          .forEach(
            mediaContent ->
              tasks.add(
                () -> {
                  mediaContent.setSignedDownloadUrl(
                    s3Service.generatePreviewUrl(mediaContent.getKey())
                  );
                  mediaContent.setSignedFullSizeObjectDownloadUrl(
                    s3Service.generateDownloadUUrl(mediaContent.getKey())
                  );
                  if (
                    mediaContent.getSizeInBytes() < 0
                  ) mediaContent.setSizeInBytes(
                    s3Service.getFullSizeMediaObjectSize(mediaContent.getKey())
                  );
                }
              )
          )
    );
    CompletableFuture<?>[] futures = tasks
      .stream()
      .map(task -> CompletableFuture.runAsync(task, executor))
      .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
  }

  @Override
  public void postUpdate(
    MediaTag originalInput,
    MediaTag toUpdate,
    MediaTag updated,
    DataManager<MediaTag> dataManager
  ) {
    if (originalInput.getAppliedScopePathPrefixes() == null) return;
    val oldVerses = toUpdate.getVerses();
    oldVerses.forEach(
      verse ->
        verse
          .getMediaTags()
          .removeIf(mediaTag -> mediaTag.getId().equals(updated.getId()))
    );
    val oldVersesIds = oldVerses
      .stream()
      .map(Verse::getId)
      .collect(Collectors.toSet());
    toUpdate
      .getVerses()
      .removeIf(oldVerse -> oldVersesIds.contains(oldVerse.getId()));
    verseDataManager.flush();
    originalInput.setKey(
      originalInput.getKey() != null
        ? originalInput.getKey()
        : toUpdate.getKey()
    );
    applyMediaTagToScope(originalInput, toUpdate);
  }

  @Override
  public void postCreate(
    MediaTag originalInput,
    MediaTag created,
    DataManager<MediaTag> dataManager
  ) {
    if (originalInput.getKey() == null) throw new IllegalArgumentException(
      "\"key\" is a required argument when creating a new MediaTag object"
    );
    applyMediaTagToScope(originalInput, created);
  }

  private void applyMediaTagToScope(MediaTag originalInput, MediaTag created) {
    val searchRequest = new FreeTextSearchPageRequest();
    searchRequest.setSortBy("path");
    searchRequest.setFetchAll(true);
    searchRequest.setSearchTerm(originalInput.getKey());
    Map<String, Object> customArgs = new HashMap<>();
    if (originalInput.getAppliedScopePathPrefixes() != null) customArgs.put(
      "validPathPrefixes",
      originalInput.getAppliedScopePathPrefixes()
    );
    searchRequest.setCustomArgs(customArgs);
    val relevantVerses = searchEngine
      .executeCustomFreeTextSearch(searchRequest, verseDataManager)
      .getContent();
    for (Verse verse : relevantVerses) {
      verse.getMediaTags().add(created);
      created.getVerses().add(verse);
    }
  }

  @Override
  public void preDelete(
    MediaTag originalInput,
    MediaTag toDelete,
    DataManager<MediaTag> dataManager
  ) {
    toDelete
      .getVerses()
      .forEach(
        verse ->
          verse
            .getMediaTags()
            .removeIf(mediaTag -> mediaTag.getId().equals(toDelete.getId()))
      );
  }
}
