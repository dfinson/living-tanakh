package living.tanach.api.utils;

import com.google.re2j.Pattern;
import java.util.*;
import java.util.function.BinaryOperator;
import lombok.Getter;
import lombok.val;

public class StaticUtils {

  public static String toHebrewNumeral(int num) {
    if (num <= 0) throw new IllegalArgumentException();
    StringBuilder hebrewNumeralBuilder = new StringBuilder(
      "ת".repeat(num / 400)
    );
    num %= 400;
    if (num >= 100) {
      hebrewNumeralBuilder.append("קרש".charAt(num / 100 - 1));
      num %= 100;
    }
    boolean wasGreaterThan10 = num > 10;
    switch (num) {
      // Avoid letter combinations from the Tetragrammaton
      case 16:
        hebrewNumeralBuilder.append("ט''ז");
        break;
      case 15:
        hebrewNumeralBuilder.append("ט''ו");
        break;
      default:
        if (num >= 10) {
          hebrewNumeralBuilder.append("יכלמנסעפצ".charAt(num / 10 - 1));
          num %= 10;
        }
        if (num > 0) {
          if (wasGreaterThan10) hebrewNumeralBuilder.append("''");
          hebrewNumeralBuilder.append("אבגדהוזחט".charAt(num - 1));
        }
        break;
    }
    return hebrewNumeralBuilder.toString();
  }

  public static <T> BinaryOperator<List<T>> defaultMergeFunction() {
    return (u, v) -> {
      throw new IllegalStateException(String.format("Duplicate key %s", u));
    };
  }

  public static Set<String> booksInTorah() {
    return new HashSet<>(
      Arrays.asList("Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy")
    );
  }

  public static Set<String> booksInProphets() {
    return new HashSet<>(
      Arrays.asList(
        "Joshua",
        "Judges",
        "Samuel I",
        "Samuel II",
        "Kings I",
        "Kings II",
        "Isaiah",
        "Jeremiah",
        "Ezekiel",
        "Hosea",
        "Joel",
        "Amos",
        "Obadiah",
        "Jonah",
        "Micha",
        "Nahum",
        "Habakkuk",
        "Zephania",
        "Haggai",
        "Zechariah",
        "Malachi"
      )
    );
  }

  public static Set<String> booksInWritings() {
    return new HashSet<>(
      Arrays.asList(
        "Psalms",
        "Proverbs",
        "Job",
        "Ruth",
        "Lamentations",
        "Ecclesiastes",
        "Esther",
        "Daniel",
        "Ezra",
        "Nehemiah",
        "Chronicles I",
        "Chronicles II"
      )
    );
  }

  private static final Map<String, String> hebrewBookNamesDict = new HashMap<>() {
    {
      put("Genesis", "בראשית");
      put("Exodus", "שמות");
      put("Leviticus", "ויקרא");
      put("Numbers", "במדבר");
      put("Deuteronomy", "דברים");

      put("Joshua", "יהושוע");
      put("Judges", "שופטים");
      put("Samuel I", "שמואל א");
      put("Samuel II", "שמואל ב");
      put("Kings I", "מלכים א");
      put("Kings II", "מלכים ב");
      put("Isaiah", "ישעיה");
      put("Jeremiah", "ירמיהו");
      put("Ezekiel", "יחזקאל");
      put("Hosea", "הושע");
      put("Joel", "יואל");
      put("Amos", "עמוס");
      put("Obadiah", "עובדיה");
      put("Jonah", "יונה");
      put("Micha", "מיכה");
      put("Nahum", "נחום");
      put("Habakkuk", "חבקוק");
      put("Zephania", "צפניה");
      put("Haggai", "חגי");
      put("Zechariah", "זכריה");
      put("Malachi", "מלאכי");
      put("Daniel", "דניאל");

      put("Psalms", "תהלים");
      put("Proverbs", "משלי");
      put("Job", "איוב");
      put("Ruth", "רות");
      put("Lamentations", "איכה");
      put("Ecclesiastes", "קוהלת");
      put("Esther", "אסתר");
      put("Ezra", "עזרא");
      put("Nehemiah", "נחמיה");
      put("Chronicles I", "דברי הימים א");
      put("Chronicles II", "דברי הימים ב");
    }
  };

  public static String toHumanReadableHebrewPath(String path) {
    val pathValues = path.split("/");
    val hebrewBookName = hebrewBookNamesDict.get(pathValues[1]);
    val chapterNumeral = toHebrewNumeral(Integer.parseInt(pathValues[2]));
    val verseNumeral = toHebrewNumeral(Integer.parseInt(pathValues[3]));
    return String.format(
      "ספר %s, פרק %s, פסוק %s",
      hebrewBookName,
      chapterNumeral,
      verseNumeral
    );
  }

  private static final Pattern isNikudOrTaamimPattern = Pattern.compile(
    "[\u05B0-\u05C4 | \u0591-\u05AF]"
  );
  private static final Pattern isStillKosherPattern = Pattern.compile(
    "[\u05BE | \\s+]"
  );

  public static boolean isPlainHebrewCharacter(char c) {
    String s = String.valueOf(c);
    return (
      !isNikudOrTaamimPattern.matches(s) || isStillKosherPattern.matches(s)
    );
  }
}
