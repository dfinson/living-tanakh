package org.maayan.maayanproject.custom_endpoints;

import dev.sanda.apifi.annotations.GraphQLComponent;
import dev.sanda.datafi.service.DataManager;
import io.leangen.graphql.annotations.GraphQLMutation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.val;
import org.maayan.maayanproject.api_hooks.LinkedMediaContentOfTagApiHooks;
import org.maayan.maayanproject.model.entities.MediaContent;
import org.maayan.maayanproject.model.entities.MediaTag;
import org.maayan.maayanproject.model.entities.Verse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
@GraphQLComponent
public class CustomMediaTagsEndpoints {

  @Autowired
  private DataManager<Verse> verseDataManager;

  @Autowired
  private DataManager<MediaContent> mediaContentDataManager;

  @Autowired
  private DataManager<MediaTag> mediaTagDataManager;

  @Autowired
  private LinkedMediaContentOfTagApiHooks linkedMediaContentOfTagApiHooks;

  private final Executor executor = Executors.newFixedThreadPool(50);

  @GraphQLMutation
  @SuppressWarnings("unchecked")
  public void deleteAllMediaTags() {
    // load all relevant verses
    List<Verse> versesWithMediaTags = verseDataManager
      .entityManager()
      .createQuery("SELECT v from Verse v WHERE v.mediaTags.size > 0")
      .getResultList();
    // create async runnable for deletion of corresponding s3 object per media content entry in DB
    List<Runnable> objectDeletionTasks = new ArrayList<>();
    versesWithMediaTags.forEach(
      verse ->
        verse
          .getMediaTags()
          .forEach(
            mediaTag ->
              objectDeletionTasks.add(
                () -> {
                  linkedMediaContentOfTagApiHooks.deleteObjectsFromS3(
                    mediaTag.getLinkedContent(),
                    mediaContentDataManager
                  );
                  mediaTag.setLinkedContent(new HashSet<>());
                }
              )
          )
    );
    CompletableFuture<?>[] futures = objectDeletionTasks
      .stream()
      .map(task -> CompletableFuture.runAsync(task, executor))
      .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
    versesWithMediaTags
      .stream()
      .filter(verse -> verse.getMediaTags() != null)
      .forEach(
        verse ->
          verse
            .getMediaTags()
            .forEach(
              mediaTag -> {
                mediaTag.setVerses(new HashSet<>());
                mediaTag.setLinkedContent(new HashSet<>());
              }
            )
      );
    versesWithMediaTags.forEach(verse -> verse.setMediaTags(new HashSet<>()));
    mediaContentDataManager.findAll().forEach(mediaContent -> mediaContent.setMediaTag(null));
    // delete orphaned db entries
    mediaContentDataManager.deleteAll();
    mediaTagDataManager.deleteAll();
  }
}
