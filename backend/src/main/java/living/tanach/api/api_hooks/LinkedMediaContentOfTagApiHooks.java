package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.EntityCollectionApiHooks;
import dev.sanda.datafi.dto.Page;
import dev.sanda.datafi.dto.PageRequest;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.entities.MediaContent;
import living.tanach.api.model.entities.MediaTag;
import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class LinkedMediaContentOfTagApiHooks implements EntityCollectionApiHooks<MediaContent, MediaTag> {

    private final S3Service s3Service;

    // set signed upload urls
    @Override
    public void postAssociate(
            Collection<MediaContent> toAssociateInput,
            Collection<MediaContent> newlyAssociated,
            MediaTag ownerInstance,
            String fieldName,
            DataManager<MediaContent> mediaContentDataManager,
            DataManager<MediaTag> ownerDataManager) {
        // ensure bi-directional synchronization
        newlyAssociated.forEach(mediaContent -> mediaContent.setMediaTag(ownerInstance));
        mediaContentDataManager.saveAll(newlyAssociated);
        // assign a transient signed upload url to each resource so that the client can upload it to S3
        newlyAssociated.forEach(mediaContent -> {
            val storageKey = mediaContent.getKey();
            val mimeType = mediaContent.getMediaType().getMimeType();
            val signedUploadUrl = s3Service.generateUploadUrl(storageKey, mimeType);
            mediaContent.setSignedUploadUrl(signedUploadUrl);
        });
    }

    // delete objects from S3
    @Override
    public void preRemove(
            Collection<MediaContent> toRemoveInput,
            MediaTag ownerInstance,
            DataManager<MediaContent> mediaContentDataManager,
            DataManager<MediaTag> ownerDataManager) {
        val ids = toRemoveInput.stream().map(MediaContent::getId).collect(Collectors.toList());
        val toRemove = mediaContentDataManager.findAllById(ids);
        val objectStorageKeys = toRemove.stream().map(MediaContent::getKey).collect(Collectors.toList());
        s3Service.deleteObjects(objectStorageKeys);
    }

    // set signed download urls
    @Override
    public void postGetPaginatedBatch(
            Page<MediaContent> resultPage,
            MediaTag ownerInstance,
            PageRequest inputRequest,
            DataManager<MediaContent> mediaContentDataManager,
            DataManager<MediaTag> ownerDataManager) {

        resultPage.getContent().forEach(mediaContent -> {
            val key = mediaContent.getKey();
            val signedDownloadUrl = s3Service.generateDownloadUrl(key);
            mediaContent.setSignedDownloadUrl(signedDownloadUrl);
        });
    }
}
