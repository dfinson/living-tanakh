package living.tanach.api.service;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.Chapter;
import living.tanach.api.model.MediaTag;
import living.tanach.api.model.Verse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VerseGraphQLApiServiceTest {

    @Autowired
    private DataManager<Verse> verseDataManager;
    @Autowired
    private TestableGraphQLService<Verse> testApi;

    @Test
    public void chapter() {
        List<Verse> firstHundredVerses = firstNVerses(100);
        List<Chapter> expected = firstHundredVerses.stream().map(Verse::getChapter).collect(Collectors.toList());
        List<Chapter> actual = testApi.invokeEndpoint("chapter", firstHundredVerses);
        assertEquals(expected, actual);
    }

    @Test
    public void mediaTags() {
        List<Verse> first20Verses = firstNVerses(20);
        setMockMediaTags(first20Verses);
        Map<Verse, List<MediaTag>> expectedTagsPerVerse =
                first20Verses.stream().collect(Collectors.toMap(Function.identity(), verse -> new ArrayList<>(verse.getMediaTags())));
        List<List<MediaTag>> result = testApi.invokeEndpoint("mediaTags", first20Verses);
        Map<Verse, List<MediaTag>> actualTagsPerVerse = new HashMap<>();
        assertEquals(first20Verses.size(), result.size());
        for (int i = 0; i < first20Verses.size(); i++)
            actualTagsPerVerse.put(first20Verses.get(i), result.get(i));
        expectedTagsPerVerse.forEach((verse, expectedMediaTags) -> {
            val actualMediaTags = actualTagsPerVerse.get(verse);
            assertEquals(expectedMediaTags, actualMediaTags);
        });
    }

    @Test
    public void associateTagsWithVerse() {
    }

    @Test
    public void updateTagsOfVerse() {
    }

    @Test
    public void removeTagsFromVerse() {
    }

    @Test
    public void verseFreeTextSearch() {
    }

    @Test
    public void findVerseByUniquePath() {
    }

    // helpers

    @SuppressWarnings("unchecked")
    private List<Verse> firstNVerses(int n) {
        return (List<Verse>)
                verseDataManager
                        .entityManager()
                        .createQuery("SELECT verse FROM Verse verse")
                        .setMaxResults(n)
                        .getResultList();
    }

    private void setMockMediaTags(List<Verse> verses) {

    }
}
