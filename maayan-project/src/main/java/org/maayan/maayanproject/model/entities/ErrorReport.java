package org.maayan.maayanproject.model.entities;

import static dev.sanda.apifi.code_generator.entity.CRUDEndpoints.CREATE;
import static dev.sanda.apifi.code_generator.entity.CRUDEndpoints.GET_PAGINATED_BATCH;

import dev.sanda.apifi.annotations.WithCRUDEndpoints;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
@WithCRUDEndpoints({ CREATE, GET_PAGINATED_BATCH })
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
