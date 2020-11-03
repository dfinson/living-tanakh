package living.tanach.api.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import living.tanach.api.model.contstants.MediaType;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
public class MediaContent {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private MediaTag mediaTag;
    @Enumerated(STRING)
    private MediaType mediaType;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String key;

    @Transient
    @Getter(onMethod_ = @GraphQLQuery)
    private String signedDownloadUrl;
    @Transient
    @Getter(onMethod_ = @GraphQLQuery)
    private String signedUploadUrl;
}
