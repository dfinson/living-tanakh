package living.tanach.api.dto;

import living.tanach.api.model.Verse;
import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static living.tanach.api.utils.StaticUtils.isHebrewCharacterOrWhitespace;
import static living.tanach.api.utils.StaticUtils.toHumanReadableHebrewPath;

@Data
public class VerseSearchResult {
    public VerseSearchResult(Verse verse, String searchTerm){
        this.humanReadableHebrewPath = toHumanReadableHebrewPath(verse.getPath());
        this.searchTerm = actualSearchTerm(verse.getFullHebrewText(), searchTerm);
        this.path = verse.getPath();
        parseAndAssignPrefixesAndSuffix(verse.getFullHebrewText());
        this.fullHebrewText = verse.getFullHebrewText();
    }

    private void parseAndAssignPrefixesAndSuffix(String fullHebrewText) {
        prefixes = new ArrayList<>();
        var lastIndex = 0;
        var nextIndex = 0;
        val searchTermLength = searchTerm.length();
        while(nextIndex != -1){
            nextIndex = fullHebrewText.indexOf(searchTerm, lastIndex);
            if(nextIndex != -1){
                prefixes.add(fullHebrewText.substring(lastIndex, nextIndex));
                lastIndex = nextIndex + searchTermLength;
            }
        }
        val lastIndexPastSearchTerm = fullHebrewText.lastIndexOf(searchTerm) + searchTermLength;
        finalSuffix = lastIndexPastSearchTerm < fullHebrewText.length() ? fullHebrewText.substring(lastIndexPastSearchTerm) : "";
    }
    private String actualSearchTerm(String verseText, String rawSearchTerm){
        val verseChars = verseText.toCharArray();
        val searchTermChars = rawSearchTerm.toCharArray();
        var verseIndex = 0;
        var searchTermIndex = 0;
        var start = -1;
        var end = -1;
        var done = false;
        var charsMatched = 0;

        while (verseIndex < verseChars.length && !done){
            val verseChar = verseChars[verseIndex];
            if(isHebrewCharacterOrWhitespace(verseChar)){
                val searchTermChar = searchTermChars[searchTermIndex];
                if(verseChar == searchTermChar){
                    if(start == -1) start = verseIndex;
                    charsMatched++;
                    searchTermIndex++;
                    if(charsMatched == searchTermChars.length){
                        end = verseIndex;
                        done = true;
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
        return verseText.substring(start, end + 1);
    }

    private String path;
    private String fullHebrewText;
    private String humanReadableHebrewPath;
    private String searchTerm;
    private List<String> prefixes;
    private String finalSuffix;
}
