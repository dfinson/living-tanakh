package living.tanach.api.model.dto;

import living.tanach.api.model.entities.Verse;
import lombok.Data;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

import static living.tanach.api.utils.StaticUtils.isHebrewCharacterOrWhitespace;

@Data
public class HighlightedVerseSegments {

    public HighlightedVerseSegments(Verse verse){
        this.segments = parsePrefixedSegmentsByTags(verse);
    }

    public HighlightedVerseSegments(Verse verse, String highlightedKeyword){
        this.segments = parsePrefixedSegmentsBySearchTerm(verse, highlightedKeyword);
    }

    private List<PrefixedVerseSegment> parsePrefixedSegmentsBySearchTerm(Verse verse, String highlightedKeyword) {
        val fullHebrewText = verse.getFullHebrewText();
        int verseIndex = 0;
        var segmentsList = new ArrayList<PrefixedVerseSegment>();
        PrefixedVerseSegment segment;

        do {
            segment = nextPrefixedSegment(fullHebrewText, highlightedKeyword, verseIndex);
            if(segment != null) {
                verseIndex = segment.nextStartFrom(verseIndex);
                segmentsList.add(segment);
            }
        } while (segment != null);
        this.finalSuffix = fullHebrewText.substring(verseIndex + 1);
        return segmentsList;
    }

    private PrefixedVerseSegment nextPrefixedSegment(String verseText, String rawHighlightedKeyword, int startFrom){

        val verseChars = verseText.toCharArray();
        val highlightedKeywordChars = rawHighlightedKeyword.toCharArray();
        var verseIndex = startFrom;
        var highlightedKeywordIndex = 0;
        var start = -1;
        var end = -1;
        var matched = false;
        var charsMatched = 0;

        while (verseIndex < verseChars.length && !matched){
            val verseChar = verseChars[verseIndex];
            if(isHebrewCharacterOrWhitespace(verseChar)){
                val searchTermChar = highlightedKeywordChars[highlightedKeywordIndex];
                if(verseChar == searchTermChar){
                    if(start == -1) start = verseIndex;
                    charsMatched++;
                    highlightedKeywordIndex++;
                    if(charsMatched == highlightedKeywordChars.length){
                        end = verseIndex;
                        matched = true;
                    }
                }else {
                    highlightedKeywordIndex = 0;
                    start = -1;
                    end = -1;
                    charsMatched = 0;
                }
            }
            verseIndex++;
        }
        if(!matched) return null;
        val prefix = verseText.substring(startFrom, start);
        val searchTerm = verseText.substring(start, end + 1);
        return new PrefixedVerseSegment(prefix, searchTerm);
    }

    private List<PrefixedVerseSegment> parsePrefixedSegmentsByTags(Verse verse) {
        val tags = verse.getMediaTags();
        val segments = new ArrayList<PrefixedVerseSegment>();
        tags.forEach(tag -> {
            
        });
        return segments;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String commonFinalSuffix(Set<String> finalSuffixes){
        val resultBuilder = new StringBuilder();
        val reversedSuffixes = finalSuffixes
                .stream()
                .map(s -> new StringBuilder(s).reverse().toString()).collect(Collectors.toSet());
        int maxLen = reversedSuffixes.stream().min(Comparator.comparingInt(String::length)).orElse("").length();
        boolean done = false;
        for (int i = 0; i < maxLen && !done; i++) {
            char c = reversedSuffixes.stream().findFirst().get().charAt(i);
            if(isCommonChar(c, i, reversedSuffixes)){
                resultBuilder.append(c);
            }else {
                done = true;
            }
        }
        return resultBuilder.reverse().toString();
    }

    private boolean isCommonChar(char c, int i, Set<String> strings){
        return strings.stream().allMatch(s -> s.toCharArray()[i] == c);
    }

    private List<PrefixedVerseSegment> segments;
    private String finalSuffix;
}
