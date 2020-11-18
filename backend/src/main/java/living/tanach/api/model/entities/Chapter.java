package living.tanach.api.model.entities;

import dev.sanda.apifi.annotations.ApiFindByUnique;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @OrderBy("number ASC")
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
