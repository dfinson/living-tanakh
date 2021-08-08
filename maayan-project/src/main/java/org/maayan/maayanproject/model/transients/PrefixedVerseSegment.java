package org.maayan.maayanproject.model.transients;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.maayan.maayanproject.model.entities.MediaTag;
import org.maayan.maayanproject.utils.StaticUtils;

@Data
@RequiredArgsConstructor
public class PrefixedVerseSegment {

  @NonNull
  private String prefix;

  @NonNull
  private String highlightedKeyword;

  private MediaTag tag;

  public String getPlainHebrewPrefix() {
    return prefix
      .codePoints()
      .mapToObj(i -> (char) i)
      .filter(StaticUtils::isPlainHebrewCharacter)
      .collect(
        StringBuilder::new,
        StringBuilder::appendCodePoint,
        StringBuilder::append
      )
      .toString();
  }

  public String getPlainHebrewHighlightedKeyword() {
    return highlightedKeyword
      .codePoints()
      .mapToObj(i -> (char) i)
      .filter(StaticUtils::isPlainHebrewCharacter)
      .collect(
        StringBuilder::new,
        StringBuilder::appendCodePoint,
        StringBuilder::append
      )
      .toString();
  }

  public int nextStartFrom(int startOffset) {
    return startOffset + prefix.length() + highlightedKeyword.length();
  }

  public int totalLength() {
    return prefix.length() + highlightedKeyword.length();
  }
}
