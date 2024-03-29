package org.maayan.maayanproject.api_hooks;

import dev.sanda.apifi.service.api_hooks.EntityCollectionApiHooks;
import dev.sanda.datafi.service.DataManager;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.val;
import org.maayan.maayanproject.model.entities.MediaTag;
import org.maayan.maayanproject.model.entities.Verse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MediaTagsOfVerseApiHooks
  implements EntityCollectionApiHooks<MediaTag, Verse> {

  @Override
  public void preAssociate(
    Collection<MediaTag> toAssociateInput,
    Verse verse,
    String fieldName,
    DataManager<MediaTag> mediaTagDataManager,
    DataManager<Verse> verseDataManager
  ) {
    val tagsAlreadyPresentKeys = verse
      .getMediaTags()
      .stream()
      .map(MediaTag::getKey)
      .collect(Collectors.toSet());
    val overlappingKeys = toAssociateInput
      .stream()
      .map(MediaTag::getKey)
      .filter(tagsAlreadyPresentKeys::contains)
      .collect(Collectors.toSet());
    /*if(!illegalOverlappingTags.isEmpty()){
            val msgBuilder = new StringBuilder("Error adding tags with keys: [");
            illegalOverlappingTags.forEach(tag -> msgBuilder.append(", \"").append(tag.getKey()).append("\""));
            msgBuilder.append("]\n Those keys are already present in the database. Did you mean to update?");
            throw new IllegalArgumentException(msgBuilder.toString());
        }*/
    verse
      .getMediaTags()
      .removeIf(tag -> overlappingKeys.contains(tag.getKey()));
  }

  @Override
  public void postRemove(
    Collection<MediaTag> toRemoveInput,
    Verse ownerInstance,
    DataManager<MediaTag> mediaTagDataManager,
    DataManager<Verse> ownerDataManager
  ) {
    toRemoveInput.forEach(
      mediaTag ->
        mediaTag
          .getVerses()
          .removeIf(verse -> verse.getId().equals(ownerInstance.getId()))
    );
    mediaTagDataManager.saveAll(toRemoveInput);
  }
}
