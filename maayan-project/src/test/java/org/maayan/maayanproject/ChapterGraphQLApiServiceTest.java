package org.maayan.maayanproject;

import static org.junit.jupiter.api.Assertions.*;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.service.DataManager;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maayan.maayanproject.model.entities.Book;
import org.maayan.maayanproject.model.entities.Chapter;
import org.maayan.maayanproject.model.entities.Verse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ChapterGraphQLApiServiceTest {

  private final TestableGraphQLService<Chapter> testApi;
  private final DataManager<Chapter> dataManager;

  // simple test to check if the findChapterByUniquePath endpoint for chapter works as expected
  @Test
  public void findChapterByUniquePath() {
    val methodName = "findChapterByUniquePath";

    // baseline: ID is valid
    val validChapterPath = "TORAH.Leviticus.1";
    Chapter chapter = testApi.invokeEndpoint(methodName, validChapterPath);
    assertNotNull(chapter);
    assertEquals(validChapterPath, chapter.getPath());

    // edge: ID is invalid
    val invalidChapterPath = "NONSENSICAL_HERESY.Matthew.1";
    assertThrows(
      RuntimeException.class,
      () -> testApi.invokeEndpoint(methodName, invalidChapterPath)
    );
  }

  @Test
  public void book() {
    val chapters = loadNChaptersWithPathPrefix("PROPHETS.Joshua", 15);
    List<Book> books = testApi.invokeEndpoint("book", chapters);
    assertTrue(
      books
        .stream()
        .allMatch(book -> book.getPath().startsWith("PROPHETS.Joshua"))
    );
  }

  @Test
  public void verses() {
    val chapters = loadNChaptersWithPathPrefix("TORAH.Genesis", 50);
    Map<Chapter, List<Verse>> expectedVerses = chapters
      .stream()
      .collect(Collectors.toMap(chapter -> chapter, Chapter::getVerses));
    List<List<Verse>> result = testApi.invokeEndpoint("verses", chapters);
    Map<Chapter, List<Verse>> actualVerses = new HashMap<>();
    for (List<Verse> verseList : result) {
      val chapter = verseList.get(0).getChapter();
      actualVerses.put(chapter, verseList);
    }
    assertEquals(expectedVerses.size(), actualVerses.size());
    for (Map.Entry<Chapter, List<Verse>> entry : expectedVerses.entrySet()) {
      verifyVerseEquality(actualVerses, entry);
    }
  }

  private void verifyVerseEquality(
    Map<Chapter, List<Verse>> actualVerses,
    Map.Entry<Chapter, List<Verse>> entry
  ) {
    List<Verse> expected = entry.getValue();
    List<Verse> actual = actualVerses.get(entry.getKey());
    expected.sort(Comparator.comparing(Verse::getNumber));
    actual.sort(Comparator.comparing(Verse::getNumber));
    List<String> expectedPaths = expected
      .stream()
      .map(Verse::getPath)
      .collect(Collectors.toList());
    List<String> actualPaths = actual
      .stream()
      .map(Verse::getPath)
      .collect(Collectors.toList());
    assertEquals(expectedPaths, actualPaths);
  }

  private List<Chapter> loadNChaptersWithPathPrefix(String prefix, int n) {
    List<String> paths = IntStream
      .rangeClosed(1, n)
      .mapToObj(i -> prefix + "." + i)
      .collect(Collectors.toList());
    return paths
      .stream()
      .map(path -> dataManager.findByUnique("path", path).orElseThrow())
      .collect(Collectors.toList());
  }
}
