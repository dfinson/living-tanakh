package living.tanach.api.model.transients;

import living.tanach.api.model.entities.MediaTag;
import living.tanach.api.utils.StaticUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

import static living.tanach.api.utils.StaticUtils.isPlainHebrewCharacter;

@Data
@RequiredArgsConstructor
public class PrefixedVerseSegment {
    @NonNull
    private String prefix;
    @NonNull
    private String highlightedKeyword;
    private MediaTag tag;

    public String getPlainHebrewPrefix(){
        return prefix.codePoints()
                .mapToObj(i -> (char)i)
                .filter(StaticUtils::isPlainHebrewCharacter)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public String getPlainHebrewHighlightedKeyword(){
        return highlightedKeyword.codePoints()
                .mapToObj(i -> (char)i)
                .filter(StaticUtils::isPlainHebrewCharacter)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public int nextStartFrom(int startOffset){
        return startOffset + prefix.length() + highlightedKeyword.length();
    }

    public int totalLength(){
        return prefix.length() + highlightedKeyword.length();
    }
}
