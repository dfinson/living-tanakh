package living.tanach.api.service;

import static living.tanach.api.utils.StaticUtils.defaultMergeFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import dev.sanda.datafi.service.DataManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import living.tanach.api.model.entities.Book;
import living.tanach.api.model.entities.Chapter;
import lombok.AllArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BookGraphQLApiServiceTest {

  private final TestableGraphQLService<Book> testApi;
  private final DataManager<Book> bookDataManager;

  // test to see if the batched fetching of the chapters field within book works as expected
  @Test
  public void chapters() {
    // load a few books
    List<Book> books = bookDataManager.findAll().subList(0, 10);
    // store the actual chapter values
    Map<Book, List<Chapter>> expectedChaptersPerBook = books
      .stream()
      .collect(
        Collectors.toMap(
          Function.identity(),
          Book::getChapters,
          defaultMergeFunction(),
          LinkedHashMap::new
        )
      );
    // invoke the "chapters" endpoint and compare
    List<List<Chapter>> results = testApi.invokeEndpoint("chapters", books);
    assertEquals(expectedChaptersPerBook.size(), results.size());
    Map<Book, List<Chapter>> actualChaptersPerBook = new LinkedHashMap<>();
    for (int i = 0; i < results.size(); i++) {
      val book = books.get(i);
      val chapters = results.get(i);
      actualChaptersPerBook.put(book, chapters);
    }
    expectedChaptersPerBook.forEach(
      (book, expectedChapters) -> {
        val actualChapters = actualChaptersPerBook.get(book);
        assertEquals(expectedChapters.size(), actualChapters.size());
        for (int i = 0; i < actualChapters.size(); i++) {
          val expectedChapter = expectedChapters.get(i);
          val actualChapter = actualChapters.get(i);
          assertEquals(expectedChapter, actualChapter);
        }
      }
    );
  }
}
