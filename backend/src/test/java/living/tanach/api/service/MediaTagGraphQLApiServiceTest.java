package living.tanach.api.service;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.MediaContent;
import living.tanach.api.model.MediaTag;
import living.tanach.api.model.Verse;
import living.tanach.api.model.contstants.MediaType;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static living.tanach.api.model.contstants.MediaType.AUDIO;
import static living.tanach.api.utils.StaticTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
class MediaTagGraphQLApiServiceTest {

    private final TestableGraphQLService<MediaTag> testApi;
    private final DataManager<MediaTag> mediaTagDataManager;
    private final DataManager<Verse> verseDataManager;
    private final DataManager<MediaContent> mediaContentDataManager;

    @Test
    void verses() {
        var verses  = versesWithMediaTagsSortedById();
        var tags = getMediaTags(verses);
        var expectedVerses = tags
                .stream()
                .map(mediaTag -> mediaTag
                        .getVerses()
                        .stream()
                        .sorted(Comparator.comparing(Verse::getId))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        List<List<Verse>> result = testApi.invokeEndpoint("verses", tags);
        result.forEach(versesList -> versesList.sort(Comparator.comparing(Verse::getId)));
        assertEquals(expectedVerses, result);
    }

    @Test
    void linkedContent() {
        var verses = versesWithMediaTagsSortedById();
        verses.forEach(this::setLinkedMediaContent);
        var tags = getMediaTags(verses);
        tags.sort(Comparator.comparing(MediaTag::getId));
        mediaTagDataManager.saveAll(tags);
        List<List<MediaContent>> expected = tags
                .stream()
                .map(tag -> tag
                        .getLinkedContent()
                        .stream()
                        .sorted(Comparator.comparing(MediaContent::getId))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        List<List<MediaContent>> actual = testApi.invokeEndpoint("linkedContent", tags);
        actual.forEach(list -> list.sort(Comparator.comparing(MediaContent::getId)));
        assertEquals(expected, actual);
    }

    @Test
    void associateLinkedContentWithMediaTag() {
        var tag = getTag();
        var input = getMediaContentObjects(tag);
        List<MediaContent> actual = testApi.invokeEndpoint("associateLinkedContentWithMediaTag", tag, input);
        List<MediaContent> expected = tag
                        .getLinkedContent()
                        .stream()
                        .sorted(Comparator.comparing(MediaContent::getDescription))
                        .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    void updateLinkedContentOfMediaTag() {
        var tag = getTag();
        var input = getMediaContentObjects(tag);
        List<MediaContent> mediaContentOfTag = testApi.invokeEndpoint("associateLinkedContentWithMediaTag", tag, input);
        List<MediaContent> updated = new ArrayList<>();
        mediaContentOfTag.forEach(originalMediaContent -> {
            var updatedMediaContent = new MediaContent();
            updatedMediaContent.setId(originalMediaContent.getId());
            updatedMediaContent.setMediaType(AUDIO);
            updatedMediaContent.setName("foo");
            updatedMediaContent.setDescription("bar");
            updatedMediaContent.setMediaTag(originalMediaContent.getMediaTag());
            updated.add(updatedMediaContent);
        });
        List<MediaContent> actual = testApi.invokeEndpoint("updateLinkedContentOfMediaTag", tag, updated);
        assertEquals(updated.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            var expectedObject = updated.get(i);
            var actualObject = actual.get(i);
            assertEquals(expectedObject.getId(), actualObject.getId());
            assertEquals(expectedObject.getDescription(), actualObject.getDescription());
            assertEquals(expectedObject.getName(), actualObject.getName());
            assertEquals(expectedObject.getMediaType(), actualObject.getMediaType());
            assertEquals(expectedObject.getMediaTag(), actualObject.getMediaTag());
        }
    }

    @Test
    void removeLinkedContentFromMediaTag() {
        var tag = getTag();
        var input = getMediaContentObjects(tag);
        List<MediaContent> mediaContentOfTag = testApi.invokeEndpoint("associateLinkedContentWithMediaTag", tag, input);
        List<MediaContent> toDelete = mediaContentOfTag
                .stream()
                .map(mediaContent -> {
                    var mediaContentToDelete = new MediaContent();
                    mediaContentToDelete.setId(mediaContent.getId());
                    return mediaContentToDelete;
                })
                .collect(Collectors.toList());
        List<MediaContent> deleted = testApi.invokeEndpoint("removeLinkedContentFromMediaTag", tag, toDelete);
        assertEquals(0, tag.getLinkedContent().size());
    }

    // helpers

    private List<Verse> versesWithMediaTagsSortedById(){
        return versesWithMediaTagsSortedById(ThreadLocalRandom.current().nextInt(3, 11));
    }
    private List<Verse> versesWithMediaTagsSortedById(int numVerses){
        var verses  = firstNVerses(numVerses, verseDataManager);
        setMockMediaTags(verses);
        verses = verseDataManager.saveAll(verses);
        verses.sort(Comparator.comparing(Verse::getId));
        return verses;
    }
    private void setLinkedMediaContent(Verse verse) {
        var tags = verse.getMediaTags();
        tags.forEach(this::setMockMediaContent);
        mediaTagDataManager.saveAll(tags);
    }
    private void setMockMediaContent(MediaTag tag) {
        var numContentObjects = ThreadLocalRandom.current().nextInt(3, 6);
        var mediaContentObjects =
                 IntStream
                .rangeClosed(0, numContentObjects)
                .mapToObj(i -> generateMockMediaContent(tag))
                .collect(Collectors.toSet());
        tag.setLinkedContent(mediaContentObjects);
    }
    private MediaContent generateMockMediaContent(MediaTag tag) {
        var faker = getFaker();
        var mediaContentObject = new MediaContent();
        mediaContentObject.setMediaTag(tag);
        mediaContentObject.setMediaType(randomMediaType());
        mediaContentObject.setName(faker.starTrek().character());
        mediaContentObject.setDescription(faker.lorem().paragraph());
        return mediaContentObject;
    }
    private List<MediaTag> getMediaTags(List<Verse> verses) {
        return verses
                .stream()
                .flatMap(verse -> {
                    var mediaTags = verse.getMediaTags();
                    var asList = new ArrayList<>(mediaTags);
                    asList.sort((Comparator.comparing(MediaTag::getId)));
                    return asList.stream();
                })
                .collect(Collectors.toList());
    }
    private MediaTag getTag() {
        return versesWithMediaTagsSortedById(1)
                .get(0)
                .getMediaTags()
                .stream()
                .min(Comparator.comparing(MediaTag::getId))
                .orElseThrow();
    }
    private List<MediaContent> getMediaContentObjects(MediaTag tag) {
        return IntStream
                .range(0, 5)
                .mapToObj(i -> generateMockMediaContent(tag))
                .sorted(Comparator.comparing(MediaContent::getDescription))
                .collect(Collectors.toList());
    }
}