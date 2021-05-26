package living.tanach.api.model.entities;

import static javax.persistence.EnumType.STRING;

import io.leangen.graphql.annotations.GraphQLQuery;
import javax.persistence.*;
import living.tanach.api.model.contstants.MediaType;
import lombok.Data;
import lombok.Getter;

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

  private Long sizeInBytes = -1L;

  public String getMimeType() {
    return mediaType.getMimeType();
  }

  @Transient
  @Getter(onMethod_ = @GraphQLQuery)
  private String signedDownloadUrl;

  @Transient
  @Getter(onMethod_ = @GraphQLQuery)
  private String signedFullSizeObjectDownloadUrl;

  @Transient
  @Getter(onMethod_ = @GraphQLQuery)
  private String signedUploadUrl;
}
