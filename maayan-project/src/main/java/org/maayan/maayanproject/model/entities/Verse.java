package org.maayan.maayanproject.model.entities;

import static dev.sanda.apifi.code_generator.entity.EntityCollectionEndpointType.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.FetchMode.JOIN;
import static org.maayan.maayanproject.utils.StaticUtils.toHebrewNumeral;
import static org.maayan.maayanproject.utils.StaticUtils.toHumanReadableHebrewPath;

import dev.sanda.apifi.annotations.ApiFindByUnique;
import dev.sanda.apifi.annotations.EntityCollectionApi;
import dev.sanda.apifi.annotations.WithApiFreeTextSearchByFields;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import org.maayan.maayanproject.api_hooks.MediaTagsOfVerseApiHooks;
import org.maayan.maayanproject.model.transients.HighlightedVerseSegments;

@Data
@Entity
@Indexed
@ToString(exclude = "chapter")
@EqualsAndHashCode(exclude = "chapter")
@Table(indexes = { @Index(name = "path", columnList = "path") })
@WithApiFreeTextSearchByFields("searchableHebrewText")
public class Verse {

  @Id
  @GeneratedValue
  @SortableField
  private Long id;

  @Field
  @SortableField
  @ApiFindByUnique
  @Column(unique = true)
  private String path;

  @SortableField
  @Field
  private Integer number;

  @Fetch(JOIN)
  @ManyToOne(fetch = LAZY, optional = false)
  private Chapter chapter;

  @Fetch(JOIN)
  @ManyToMany(cascade = ALL, fetch = LAZY)
  @EntityCollectionApi(
    endpoints = { ASSOCIATE_WITH, UPDATE_IN, REMOVE_FROM },
    apiHooks = MediaTagsOfVerseApiHooks.class
  )
  private Set<MediaTag> mediaTags = new HashSet<>();

  @Column(columnDefinition = "TEXT")
  private String fullHebrewText;

  @Column(columnDefinition = "TEXT")
  private String fullEnglishText;

  @Field
  @Column(columnDefinition = "TEXT")
  private String searchableHebrewText;

  public String getHebrewNumeral() {
    return toHebrewNumeral(this.number);
  }

  public String getHumanReadablePath() {
    return toHumanReadableHebrewPath(path);
  }

  @Transient
  @Getter(onMethod_ = @GraphQLQuery)
  private HighlightedVerseSegments highlightedVerseSegments;
}
