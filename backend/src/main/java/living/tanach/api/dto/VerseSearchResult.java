package living.tanach.api.dto;

import living.tanach.api.model.Verse;
import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static living.tanach.api.utils.StaticUtils.isHebrewCharacterOrWhitespace;
import static living.tanach.api.utils.StaticUtils.toHumanReadableHebrewPath;

@Data
public class VerseSearchResult {
    public VerseSearchResult(Verse verse, String searchTerm){
        this.humanReadableHebrewPath = toHumanReadableHebrewPath(verse.getPath());
        this.segments = parsePrefixedSegments(verse.getFullHebrewText(), searchTerm);
        this.path = verse.getPath();
        this.fullHebrewText = verse.getFullHebrewText();
    }

    private List<PrefixedVerseSegment> parsePrefixedSegments(String fullHebrewText, String searchTerm) {
        int verseIndex = 0;
        var segmentsList = new ArrayList<PrefixedVerseSegment>();
        PrefixedVerseSegment segment = null;

        do {
            segment = nextPrefixedSegment(fullHebrewText, searchTerm, verseIndex);
            if(segment != null) {
                verseIndex = segment.nextStartFrom(verseIndex);
                segmentsList.add(segment);
            }
        }
        while (segment != null);
        this.finalSuffix = fullHebrewText.substring(verseIndex + 1);
        return segmentsList;
    }

    private PrefixedVerseSegment nextPrefixedSegment(String verseText, String rawSearchTerm, int startFrom){

        val verseChars = verseText.toCharArray();
        val searchTermChars = rawSearchTerm.toCharArray();
        var verseIndex = startFrom;
        var searchTermIndex = 0;
        var start = -1;
        var end = -1;
        var matched = false;
        var charsMatched = 0;

        while (verseIndex < verseChars.length && !matched){
            val verseChar = verseChars[verseIndex];
            if(isHebrewCharacterOrWhitespace(verseChar)){
                val searchTermChar = searchTermChars[searchTermIndex];
                if(verseChar == searchTermChar){
                    if(start == -1) start = verseIndex;
                    charsMatched++;
                    searchTermIndex++;
                    if(charsMatched == searchTermChars.length){
                        end = verseIndex;
                        matched = true;
                    }
                }else {
                    searchTermIndex = 0;
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

    private String path;
    private String fullHebrewText;
    private String humanReadableHebrewPath;
    private List<PrefixedVerseSegment> segments;
    private String finalSuffix;
}
