package living.tanach.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrefixedVerseSegment {
    private String prefix;
    private String searchTerm;

    public int nextStartFrom(int startOffset){
        return startOffset + prefix.length() + searchTerm.length();
    }
}
