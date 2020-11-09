package living.tanach.api.model;


import dev.sanda.apifi.annotations.ApiFindByUnique;
import living.tanach.api.model.contstants.TanakhSection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static org.hibernate.annotations.FetchMode.JOIN;

@Data
@Entity
@ToString(exclude = "chapters")
@EqualsAndHashCode(exclude = "chapters")
@Table(indexes = {@Index(name = "path", columnList = "path")})
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @ApiFindByUnique
    @Column(unique = true)
    private String path;
    @Enumerated(STRING)
    private TanakhSection tanakhSection;
    private String englishName;
    private String hebrewName;
    @Fetch(JOIN)
    @OrderBy("number")
    @OneToMany(cascade = ALL, mappedBy = "book")
    private List<Chapter> chapters;

    public void setChapters(Collection<Chapter> chaptersToAdd){
        this.chapters = new ArrayList<>();
        for(Chapter chapter : chaptersToAdd){
            this.chapters.add(chapter);
            chapter.setBook(this);
        }
    }
}
