package living.tanach.api.model.dto;

import living.tanach.api.model.entities.MediaTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
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
}
