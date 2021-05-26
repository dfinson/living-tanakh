package living.tanach.api.model.contstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MediaType {
  VIDEO("application/video"),
  IMAGE("application/image"),
  AUDIO("application/audio"),
  DOCUMENT("application/octet-stream"),
  OTHER("application/octet-stream");

  private final String mimeType;
}
