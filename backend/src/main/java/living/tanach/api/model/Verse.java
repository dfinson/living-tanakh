package living.tanach.api.model;

import dev.sanda.apifi.annotations.ApiFindByUnique;
import dev.sanda.apifi.annotations.EntityCollectionApi;
import dev.sanda.apifi.annotations.WithApiFreeTextSearchByFields;
import living.tanach.api.api_hooks.MediaTagsOfVerseApiHooks;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.SortableField;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static dev.sanda.apifi.generator.entity.EntityCollectionEndpointType.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static living.tanach.api.utils.StaticUtils.toHebrewNumeral;
import static org.hibernate.annotations.FetchMode.JOIN;
import static org.hibernate.search.annotations.Index.NO;

@Data
@Entity
@Indexed
@ToString(exclude = "chapter")
@EqualsAndHashCode(exclude = "chapter")
@Table(indexes = {@Index(name = "path", columnList = "path")})
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
            endpoints = {ASSOCIATE_WITH, UPDATE_IN, REMOVE_FROM},
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

    public String getHebrewNumeral(){
        return toHebrewNumeral(this.number);
    }
}
