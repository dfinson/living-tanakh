package org.maayan.maayanproject.model.entities;

import static dev.sanda.apifi.code_generator.entity.CRUDEndpoints.*;
import static dev.sanda.apifi.code_generator.entity.EntityCollectionEndpointType.*;
import static javax.persistence.CascadeType.ALL;
import static org.hibernate.annotations.FetchMode.JOIN;

import dev.sanda.apifi.annotations.EntityCollectionApi;
import dev.sanda.apifi.annotations.WithCRUDEndpoints;
import dev.sanda.datafi.annotations.attributes.AutoSynchronized;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.maayan.maayanproject.api_hooks.LinkedMediaContentOfTagApiHooks;

@Data
@Entity
@WithCRUDEndpoints({ GET_BATCH_BY_IDS, CREATE, UPDATE, DELETE })
@ToString(exclude = { "verses", "linkedContent" })
@EqualsAndHashCode(exclude = { "verses", "linkedContent" })
public class MediaTag {

  @Id
  @GeneratedValue
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String key;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToMany
  @AutoSynchronized
  private Set<Verse> verses = new HashSet<>();

  @Fetch(JOIN)
  @OneToMany(cascade = ALL)
  @EntityCollectionApi(
    endpoints = { ASSOCIATE_WITH, UPDATE_IN, REMOVE_FROM },
    apiHooks = LinkedMediaContentOfTagApiHooks.class
  )
  private Set<MediaContent> linkedContent = new HashSet<>();

  @Transient
  @Getter(onMethod_ = @GraphQLQuery)
  private List<String> appliedScopePathPrefixes;
}
