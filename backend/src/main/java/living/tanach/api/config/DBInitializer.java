package living.tanach.api.config;

import com.google.common.collect.ImmutableSet;
import com.mashape.unirest.http.Unirest;
import dev.sanda.datafi.service.DataManager;
import living.tanach.api.model.entities.Book;
import living.tanach.api.model.entities.Chapter;
import living.tanach.api.model.entities.Verse;
import living.tanach.api.model.contstants.TanakhSection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static living.tanach.api.model.contstants.TanakhSection.*;
import static living.tanach.api.utils.StaticUtils.*;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DBInitializer {

    @NonNull
    private final DataManager<Book> bookDataManager;
    @NonNull
    private final EnvironmentVariablesService env;
    @Value("${sefaria.texts-api-base-url}")
    private String sefariaTextsApiBaseUrl;

    @Transactional
    @PostConstruct
    public void init(){
        log.info("entering tanakh db initialization...");
        initTanakh();
    }


    private void initTanakh() {
        // if db is initialized we stop here
        long bookCount = (Long) bookDataManager
                .entityManager()
                .createQuery("SELECT COUNT(book.id) FROM Book book")
                .getSingleResult();

        if(bookCount > 0) {
            log.info("db already initialized - returning");
            return;
        }
        log.info("starting db init process");
        // init threadpool - each book will be fetched from sefaria on its own thread
        Executor executor = Executors.newFixedThreadPool(50);
        List<Book> books = new ArrayList<>();
        Set<String> allBooks = ImmutableSet.of(
                booksInTorah(),
                booksInProphets(),
                booksInWritings()
        ).stream().flatMap(Collection::stream).collect(Collectors.toSet());
        // create a Lock object for thread safety in critical section ahead
        Lock lock = new ReentrantLock();
        // create a Runnable task for each book
        List<Runnable> tasks =
                 allBooks
                .stream()
                 .map(bookName -> (Runnable) () -> {
                     val book = initBook(bookName);
                     lock.lock();
                     books.add(book);
                     lock.unlock();
                 })
                .collect(Collectors.toList());
        // execute all tasks asynchronously
        log.info("starting sefaria API calls and result parsing");
        CompletableFuture<?>[] futures = tasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executor))
                .toArray(CompletableFuture[]::new);
        // wait for all of the tasks to be completed
        CompletableFuture.allOf(futures).join();
        // save the books along with their chapters and verses (using cascading)
        log.info("Sefaria API calls and result parsing complete");
        bookDataManager.saveAllAndFlush(books);
        log.info("saved " + books.size() + " books to db - returning");
    }

    private Book initBook(String bookName){
        try {
            val response = Unirest
                    .get(sefariaTextsApiBaseUrl + bookName)
                    .queryString("pad", 0)
                    .asJson();
            return jsonToBook(response.getBody().getObject(), bookName);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Book jsonToBook(JSONObject json, String bookName){
        val book = new Book();
        val hebrewName = json.getString("heRef");
        val tanakhSection = determineTanakhSection(bookName);
        val path = tanakhSection.toString() + "/" + bookName;
        book.setEnglishName(bookName);
        book.setHebrewName(hebrewName);
        book.setTanakhSection(tanakhSection);
        book.setPath(path);
        book.setChapters(parseChapters(json, path));
        return book;
    }

    private List<Chapter> parseChapters(JSONObject json, String path) {
        val rawChaptersHebrew = json.getJSONArray("he");
        val rawChaptersEnglish = json.getJSONArray("text");
        int numChapters = rawChaptersEnglish.length();
        List<Chapter> chapters = new ArrayList<>();
        for (int i = 0; i < numChapters; i++)
            chapters.add(parseChapter(rawChaptersHebrew.getJSONArray(i), rawChaptersEnglish.getJSONArray(i), path, i + 1));
        return chapters;
    }

    private Chapter parseChapter(JSONArray hebrewText, JSONArray englishText, String parentPath, int chapterNumber) {
        Chapter chapter = new Chapter();
        val path = parentPath + "/" + chapterNumber;
        chapter.setPath(path);
        chapter.setNumber(chapterNumber);
        chapter.setVerses(parseVerses(hebrewText, englishText, path));
        return chapter;
    }

    private List<Verse> parseVerses(JSONArray hebrewText, JSONArray englishText, String path) {
        List<Verse> verses = new ArrayList<>();
        int numVerses = Math.min(hebrewText.length(), englishText.length());
        for (int i = 0; i < numVerses; i++)
            verses.add(parseVerse(hebrewText.getString(i), englishText.getString(i), path, i + 1));
        return verses;
    }

    private Verse parseVerse(String hebrewText, String englishText, String parentPath, int verseNumber) {
        val path = parentPath + "/" + verseNumber;
        Verse verse = new Verse();
        verse.setPath(path);
        verse.setFullEnglishText(englishText.replaceAll("<i>", "").replaceAll("</i>", ""));
        //hebrewText = handlePetuchaAndSetuma(hebrewText);
        verse.setFullHebrewText(hebrewText);
        verse.setNumber(verseNumber);
        verse.setSearchableHebrewText(parseSearchableHebrewText(hebrewText));
        return verse;
    }

/*    private String handlePetuchaAndSetuma(String hebrewText) {
        return hebrewText
                .replaceAll("(ס)", " (ס) ")
                .replaceAll("(פ)",  " (פ) " + "\n");
    }*/

    private String parseSearchableHebrewText(String hebrewText) {
        StringBuilder textBuilder = new StringBuilder();
        for (char c : hebrewText.toCharArray())
            if (isPlainHebrewCharacter(c))
                textBuilder.append(c);
        return textBuilder.toString();
    }


    private TanakhSection determineTanakhSection(String englishName) {
        if(booksInTorah().contains(englishName))
            return TORAH;
        else if(booksInProphets().contains(englishName))
            return PROPHETS;
        else
            return WRITINGS;
    }
}
