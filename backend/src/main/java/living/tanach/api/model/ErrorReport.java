package living.tanach.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class ErrorReport {
    @Id
    @GeneratedValue
    private Long id;
    private Date occurredAt = new Date();
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(length = 1000000)
    private String stacktrace;
}
