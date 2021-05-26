package living.tanach.api.model.transients;

import static living.tanach.api.utils.StaticUtils.isPlainHebrewCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import living.tanach.api.model.entities.Verse;
import living.tanach.api.utils.StaticUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Data
@Slf4j
public class HighlightedVerseSegments {

  public HighlightedVerseSegments(Verse verse) {
    this.segments = parsePrefixedSegmentsByTags(verse);
    var cumulativeSegmentsLen = segments
      .stream()
      .map(PrefixedVerseSegment::totalLength)
      .reduce(Integer::sum)
      .orElse(0);
    this.finalSuffix =
      verse.getFullHebrewText().substring(cumulativeSegmentsLen);
  }

  public HighlightedVerseSegments(Verse verse, String highlightedKeyword) {
    this.segments =
      parsePrefixedSegmentsByKeyword(verse, highlightedKeyword, false);
  }

  private List<PrefixedVerseSegment> parsePrefixedSegmentsByKeyword(
    Verse verse,
    String highlightedKeyword,
    boolean includeRafeh
  ) {
    val fullHebrewText = verse.getFullHebrewText();
    int verseIndex = 0;
    var segmentsList = new ArrayList<PrefixedVerseSegment>();
    PrefixedVerseSegment segment;

    do {
      segment =
        nextPrefixedSegment(
          fullHebrewText,
          highlightedKeyword,
          verseIndex,
          includeRafeh
        );
      if (segment != null) {
        verseIndex = segment.nextStartFrom(verseIndex);
        segmentsList.add(segment);
      }
    } while (segment != null);
    this.finalSuffix = fullHebrewText.substring(verseIndex + 1);
    return segmentsList;
  }

  private PrefixedVerseSegment nextPrefixedSegment(
    String verseText,
    String rawHighlightedKeyword,
    int startFrom,
    boolean includeRafeh
  ) {
    val verseChars = verseText.toCharArray();
    val highlightedKeywordChars = rawHighlightedKeyword.toCharArray();
    var verseIndex = startFrom;
    var highlightedKeywordIndex = 0;
    var start = -1;
    var end = -1;
    var matched = false;
    var charsMatched = 0;

    while (verseIndex < verseChars.length && !matched) {
      val verseChar = verseChars[verseIndex];
      if (
        isPlainHebrewCharacter(verseChar) &&
        (!includeRafeh || verseChar != '\u05BE')
      ) {
        val searchTermChar = highlightedKeywordChars[highlightedKeywordIndex];
        if (isMatchingCharacter(verseChar, searchTermChar)) {
          if (start == -1) start = verseIndex;
          charsMatched++;
          highlightedKeywordIndex++;
          if (charsMatched == highlightedKeywordChars.length) {
            end = verseIndex;
            matched = true;
          }
        } else {
          if (
            isRepeatOfMatchingCharacter(
              highlightedKeywordIndex,
              verseChar,
              highlightedKeywordChars
            )
          ) verseIndex--;
          highlightedKeywordIndex = 0;
          start = -1;
          end = -1;
          charsMatched = 0;
        }
      }
      verseIndex++;
    }
    if (!matched) return null;
    val prefix = verseText.substring(startFrom, start);
    val searchTerm = verseText.substring(start, end + 1);
    return new PrefixedVerseSegment(prefix, searchTerm);
  }

  private List<PrefixedVerseSegment> parsePrefixedSegmentsByTags(Verse verse) {
    val tags = verse.getMediaTags();
    val segmentsTemp = new ArrayList<PrefixedVerseSegment>();

    if (tags.isEmpty()) segmentsTemp.add(
      new PrefixedVerseSegment(verse.getFullHebrewText(), "")
    );

    tags.forEach(
      tag -> {
        val tagSegments = parsePrefixedSegmentsByKeyword(
          verse,
          tag.getKey(),
          true
        );
        tagSegments.forEach(segment -> segment.setTag(tag));
        segmentsTemp.addAll(tagSegments);
      }
    );

    return mergeSegmentPrefixes(segmentsTemp, verse.getFullHebrewText());
  }

  private List<PrefixedVerseSegment> mergeSegmentPrefixes(
    List<PrefixedVerseSegment> segmentsTemp,
    String fullText
  ) {
    if (segmentsTemp.size() == 1) return segmentsTemp;
    val finalSegments = new ArrayList<PrefixedVerseSegment>();
    val prefixToSegmentMap = segmentsTemp
      .stream()
      .collect(
        Collectors.toMap(PrefixedVerseSegment::getPrefix, Function.identity())
      );
    var currentPrefix = new StringBuilder();
    char[] charArray = fullText.toCharArray();
    for (
      int i = 0, charArrayLength = charArray.length;
      i < charArrayLength;
      i++
    ) {
      char c = charArray[i];
      currentPrefix.append(c);
      var prefixKey = currentPrefix.toString();
      var keyword = "";
      var matchedSegment = false;
      if (prefixToSegmentMap.containsKey(prefixKey)) {
        keyword = prefixToSegmentMap.get(prefixKey).getHighlightedKeyword();
        for (PrefixedVerseSegment segment : segmentsTemp) {
          var segmentPrefix = segment.getPrefix();
          if (!segmentPrefix.equals(prefixKey)) {
            var updatedSegmentPrefix = segmentPrefix.replaceFirst(
              prefixKey + keyword,
              ""
            );
            prefixToSegmentMap.remove(segment.getPrefix());
            matchedSegment = !updatedSegmentPrefix.equals(segmentPrefix);
            segment.setPrefix(updatedSegmentPrefix);
            prefixToSegmentMap.put(updatedSegmentPrefix, segment);
          }
        }
        finalSegments.add(prefixToSegmentMap.get(prefixKey));
        currentPrefix = new StringBuilder();
      }
      if (matchedSegment) i += keyword.length();
    }
    return finalSegments;
  }

  private List<PrefixedVerseSegment> segments;
  private String finalSuffix = "";

  public String getPlainHebrewFinalSuffix() {
    return finalSuffix
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

  private boolean isRepeatOfMatchingCharacter(
    int highlightedKeywordIndex,
    char verseChar,
    char[] highlightedKeywordChars
  ) {
    return (
      highlightedKeywordIndex > 0 &&
      verseChar == highlightedKeywordChars[highlightedKeywordIndex - 1]
    );
  }

  private boolean isMatchingCharacter(char verseChar, char searchTermChar) {
    return verseChar == searchTermChar;
  }
}
