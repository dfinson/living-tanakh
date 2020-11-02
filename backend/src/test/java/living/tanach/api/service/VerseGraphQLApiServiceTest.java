package living.tanach.api.service;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.dto.FreeTextSearchPageRequest;
import dev.sanda.datafi.dto.Page;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.Chapter;
import living.tanach.api.model.MediaTag;
import living.tanach.api.model.Verse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static living.tanach.api.utils.StaticTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class VerseGraphQLApiServiceTest {

    private final DataManager<Verse> verseDataManager;
    private final DataManager<MediaTag> tagDataManager;
    private final TestableGraphQLService<Verse> testApi;

    @Test
    public void chapter() {
        List<Verse> firstHundredVerses = firstNVerses(100, verseDataManager);
        List<Chapter> expected = firstHundredVerses.stream().map(Verse::getChapter).collect(Collectors.toList());
        List<Chapter> actual = testApi.invokeEndpoint("chapter", firstHundredVerses);
        assertEquals(expected, actual);
    }

    @Test
    public void mediaTags() {
        List<Verse> first20Verses = firstNVerses(20, verseDataManager);
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
        Verse verse = firstNVerses(1, verseDataManager).get(0);
        List<MediaTag> tags = IntStream.range(0, 5).mapToObj( i-> generateMockMediaTag()).collect(Collectors.toList());
        List<MediaTag> result = testApi.invokeEndpoint("associateMediaTagsWithVerse", verse, tags);
        tags.forEach(tag -> assertTrue(verse.getMediaTags().contains(tag)));
        assertEquals(tags, result);
    }

    @Test
    public void updateMediaTagsOfVerse() {

        Verse verse = firstNVerses(1, verseDataManager).get(0);
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
    public void removeMediaTagsFromVerse() {
        Verse verse = firstNVerses(1, verseDataManager).get(0);
        List<MediaTag> input = IntStream.range(0, 5).mapToObj( i-> generateMockMediaTag()).collect(Collectors.toList());
        List<MediaTag> associatedTags = testApi.invokeEndpoint("associateMediaTagsWithVerse", verse, input);
        assertEquals(verse.getMediaTags().size(), associatedTags.size());
        List<MediaTag> disassociatedTags = testApi.invokeEndpoint("removeMediaTagsFromVerse", verse, associatedTags);
        assertEquals(associatedTags.size(), disassociatedTags.size());
        assertEquals(0, verse.getMediaTags().size());
    }

    @Test
    public void verseFreeTextSearch() {
        var methodName = "verseFreeTextSearch";
        var searchTerm = "חרב";
        var request = new FreeTextSearchPageRequest();
        request.setSearchTerm(searchTerm);
        request.setFetchAll(true);
        Page<Verse> result = testApi.invokeEndpoint(methodName, request);
        assertEquals(99, result.getContent().size());
    }

    @Test
    public void findVerseByUniquePath() {
        var methodName = "findVerseByUniquePath";
        var verse = firstNVerses(1, verseDataManager).get(0);
        var path = verse.getPath();
        var result = testApi.invokeEndpoint(methodName, path);
        assertEquals(verse, result);
    }
}
