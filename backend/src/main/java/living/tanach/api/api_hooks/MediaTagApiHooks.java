package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.ApiHooks;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.entities.MediaTag;
import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MediaTagApiHooks implements ApiHooks<MediaTag> {

    private final S3Service s3Service;

    @Override
    public void postGetById(MediaTag result, DataManager<MediaTag> dataManager) {
        result
                .getLinkedContent()
                .forEach(mediaContent ->
                         mediaContent.setSignedDownloadUrl(
                                 s3Service.generateDownloadUrl(mediaContent.getKey())
                         )
                );
    }
}
