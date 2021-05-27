package org.maayan.maayanproject.utils;

import com.github.javafaker.Faker;
import dev.sanda.datafi.service.DataManager;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.val;
import org.maayan.maayanproject.model.contstants.MediaType;
import org.maayan.maayanproject.model.entities.MediaTag;
import org.maayan.maayanproject.model.entities.Verse;

public class StaticTestUtils {

  @Getter
  private static final Faker faker = new Faker();

  public static MediaTag generateMockMediaTag() {
    val tag = new MediaTag();
    tag.setKey(faker.lordOfTheRings().location());
    tag.setDescription(faker.lorem().paragraph());
    return tag;
  }

  public static void setMockMediaTags(List<Verse> verses) {
    verses.forEach(
      verse -> {
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {
          var tag = generateMockMediaTag();
          verse.getMediaTags().add(tag);
          tag.getVerses().add(verse);
        }
      }
    );
  }

  public static MediaType randomMediaType() {
    var mediaTypes = MediaType.values();
    var randomIndex = ThreadLocalRandom.current().nextInt(0, mediaTypes.length);
    return mediaTypes[randomIndex];
  }

  @SuppressWarnings("unchecked")
  public static List<Verse> firstNVerses(
    int n,
    DataManager<Verse> verseDataManager
  ) {
    return (List<Verse>) verseDataManager
      .entityManager()
      .createQuery("SELECT verse FROM Verse verse")
      .setMaxResults(n)
      .getResultList();
  }
}
