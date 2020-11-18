package living.tanach.api.model.dto;

import living.tanach.api.model.entities.MediaTag;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PrefixedVerseSegment {
    @NonNull
    private String prefix;
    @NonNull
    private String highlightedKeyword;
    private MediaTag tag;

    public int nextStartFrom(int startOffset){
        return startOffset + prefix.length() + highlightedKeyword.length();
    }

    public int totalLength(){
        return prefix.length() + highlightedKeyword.length();
    }
}
