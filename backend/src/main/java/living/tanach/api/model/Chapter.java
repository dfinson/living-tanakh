package living.tanach.api.model;

import dev.sanda.apifi.annotations.ApiFindByUnique;
import dev.sanda.apifi.annotations.WithCRUDEndpoints;
import dev.sanda.apifi.generator.entity.CRUDEndpoints;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dev.sanda.apifi.generator.entity.CRUDEndpoints.GET_BY_ID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static living.tanach.api.utils.StaticUtils.toHebrewNumeral;
import static org.hibernate.annotations.FetchMode.JOIN;

@Data
@Entity
@ToString(exclude = {"book", "verses"})
@EqualsAndHashCode(exclude = {"book", "verses"})
@Table(indexes = {@Index(name = "path", columnList = "path")})
public class Chapter {
    @Id
    @GeneratedValue
    private Long id;
    @ApiFindByUnique
    @Column(unique = true)
    private String path;
    private Integer number;
    @ManyToOne(fetch = LAZY)
    private Book book;

    @Fetch(JOIN)
    @OrderBy("number")
    @OneToMany(cascade = ALL, mappedBy = "chapter")
    private List<Verse> verses;

    public void setVerses(Collection<Verse> versesToAdd){
        this.verses = new ArrayList<>();
        for (Verse verse : versesToAdd){
            this.verses.add(verse);
            verse.setChapter(this);
        }
    }

    public String getHebrewNumeral(){
        return toHebrewNumeral(this.number);
    }
}
