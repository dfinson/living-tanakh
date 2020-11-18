package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.ApiHooks;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.dto.HighlightedVerseSegments;
import living.tanach.api.model.entities.Chapter;
import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ChapterApiHooks implements ApiHooks<Chapter> {

    private final S3Service s3Service;

    @Override
    public void postApiFindByUnique(String fieldName, Object fieldValue, Chapter result, DataManager<Chapter> dataManager) {
        result.getVerses().forEach(verse -> verse.setHighlightedVerseSegments(new HighlightedVerseSegments(verse)));
    }
}
