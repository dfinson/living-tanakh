package living.tanach.api.api_hooks;

import dev.sanda.apifi.service.api_hooks.ApiHooks;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.entities.Chapter;
import living.tanach.api.model.transients.HighlightedVerseSegments;
import org.springframework.stereotype.Service;

@Service
public class ChapterApiHooks implements ApiHooks<Chapter> {
    @Override
    public void postApiFindByUnique(String fieldName, Object fieldValue, Chapter result, DataManager<Chapter> dataManager) {
        result.getVerses().forEach(verse -> verse.setHighlightedVerseSegments(new HighlightedVerseSegments(verse)));
    }
}
