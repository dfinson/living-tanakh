package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.EntityCollectionApiHooks;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.entities.MediaTag;
import living.tanach.api.model.entities.Verse;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MediaTagsOfVerseApiHooks implements EntityCollectionApiHooks<MediaTag, Verse> {
    @Override
    public void postAssociate(
            Collection<MediaTag> toAssociateInput,
            Collection<MediaTag> newlyAssociated,
            Verse ownerInstance,
            String fieldName,
            DataManager<MediaTag> mediaTagDataManager,
            DataManager<Verse> ownerDataManager) {
        newlyAssociated.forEach(mediaTag -> mediaTag.getVerses().add(ownerInstance));
        mediaTagDataManager.saveAll(newlyAssociated);
    }

    @Override
    public void postRemove(Collection<MediaTag> toRemoveInput, Verse ownerInstance, DataManager<MediaTag> mediaTagDataManager, DataManager<Verse> ownerDataManager) {
        toRemoveInput.forEach(mediaTag -> mediaTag.getVerses().removeIf(verse -> verse.getId().equals(ownerInstance.getId())));
        mediaTagDataManager.saveAll(toRemoveInput);
    }
}
