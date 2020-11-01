package living.tanach.api.utils;

import living.tanach.api.model.Chapter;
import lombok.val;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StaticUtils {
    public static String toHebrewNumeral(int num) {
        if(num <= 0)
            throw new IllegalArgumentException();
        StringBuilder ret = new StringBuilder("ת".repeat(num / 400));
        int index;
        num %= 400;
        if(num >= 100)
        {
            ret.append("קרש".charAt(num / 100 - 1));
            num %= 100;
        }
        boolean wasGreaterThan10 = false;
        switch(num)
        {
            // Avoid letter combinations from the Tetragrammaton
            case 16:
                ret.append("ט\"ז");
                ret.reverse();
                break;
            case 15:
                ret.append("ט\"ו");
                ret.reverse();
                break;
            default:
                if (num >= 10)
                {
                    wasGreaterThan10 = num > 10;
                    ret.append("יכלמנסעפצ".charAt(num / 10 - 1));
                    num %= 10;
                }
                if(num > 0) {
                    if (wasGreaterThan10 && !ret.toString().contains("\""))
                        ret.append("\"");
                    ret.append ( "אבגדהוזחט".charAt(num - 1));
                    if(!wasGreaterThan10) {
                        ret.append("'");
                    }else {
                        ret.reverse();
                    }
                }
                break;
        }
        return ret.toString();
    }
    public static <T> BinaryOperator<List<T>> defaultMergeFunction()  {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    };
    public static Set<String> booksInTorah(){
        return new HashSet<>(
                Arrays.asList(
                        "Genesis",
                        "Exodus",
                        "Leviticus",
                        "Numbers",
                        "Deuteronomy"
                )
        );
    }
    public static Set<String> booksInProphets(){
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
                        "Malachi",
                        "Daniel",
                        "Ezra",
                        "Nehemiah",
                        "Chronicles I",
                        "Chronicles II"
                )
        );
    }
    public static Set<String> booksInWritings(){
        return new HashSet<>(
                Arrays.asList(
                        "Psalms",
                        "Proverbs",
                        "Job",
                        "Ruth",
                        "Lamentations",
                        "Ecclesiastes",
                        "Esther"
                )
        );
    }
}
