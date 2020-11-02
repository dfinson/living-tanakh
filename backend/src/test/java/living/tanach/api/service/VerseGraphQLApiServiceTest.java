package living.tanach.api.service;

import com.github.javafaker.Faker;
import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.Chapter;
import living.tanach.api.model.MediaTag;
import living.tanach.api.model.Verse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VerseGraphQLApiServiceTest {

    @Autowired
    private DataManager<Verse> verseDataManager;
    @Autowired
    private DataManager<MediaTag> tagDataManager;
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
    public void associateMediaTagsWithVerse() {
        Verse verse = firstNVerses(1).get(0);
        List<MediaTag> tags = IntStream.range(0, 5).mapToObj( i-> generateMockMediaTag()).collect(Collectors.toList());
        List<MediaTag> result = testApi.invokeEndpoint("associateMediaTagsWithVerse", verse, tags);
        tags.forEach(tag -> assertTrue(verse.getMediaTags().contains(tag)));
        assertEquals(tags, result);
    }

    @Test
    public void updateMediaTagsOfVerse() {

        Verse verse = firstNVerses(1).get(0);
        List<MediaTag> tags = IntStream.range(0, 5).mapToObj( i-> generateMockMediaTag()).collect(Collectors.toList());
        verse.setMediaTags(new HashSet<>(tags));
        verse.getMediaTags().forEach(tag -> tag.getVerses().add(verse));
        verseDataManager.save(verse);
        tagDataManager.saveAll(tags);

        List<MediaTag> toUpdate = tags.stream().map(currentTag -> {
            val updatedTag = new MediaTag();
            updatedTag.setId(currentTag.getId());
            updatedTag.setTitle("test title");
            updatedTag.setDescription("test description");
            return updatedTag;
        }).collect(Collectors.toList());

        List<MediaTag> result = testApi.invokeEndpoint("updateMediaTagsOfVerse", verse, toUpdate);

        result.forEach(tag -> {
            assertEquals("test title", tag.getTitle());
            assertEquals("test description", tag.getDescription());
        });
    }

    @Test
    public void removeTagsFromVerse() {
        Verse verse = firstNVerses(1).get(0);
        List<MediaTag> input = IntStream.range(0, 5).mapToObj( i-> generateMockMediaTag()).collect(Collectors.toList());
        List<MediaTag> associatedTags = testApi.invokeEndpoint("associateMediaTagsWithVerse", verse, input);
        assertEquals(verse.getMediaTags().size(), associatedTags.size());
        List<MediaTag> disassociatedTags = testApi.invokeEndpoint("removeTagsFromVerse", associatedTags);
        assertEquals(associatedTags.size(), disassociatedTags.size());
        assertEquals(0, verse.getMediaTags().size());
    }

    @Test
    public void verseFreeTextSearch() {
    }

    @Test
    public void findVerseByUniquePath() {
    }

    // helpers

    private final Faker faker = new Faker();

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
        verses.forEach(verse -> {
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {
                var tag = generateMockMediaTag();
                verse.getMediaTags().add(tag);
                tag.getVerses().add(verse);
            }
        });
    }

    private MediaTag generateMockMediaTag(){
        val tag = new MediaTag();
        tag.setTitle(faker.lordOfTheRings().location());
        tag.setDescription(faker.lorem().paragraph());
        return tag;
    }
}
