package living.tanach.api.model.contstants;

import java.util.*;

public enum TanakhSection {
  TORAH,
  PROPHETS,
  WRITINGS;

  public static TanakhSection fromString(String name) {
    switch (name) {
      case "Torah":
        return TORAH;
      case "Prophets":
        return PROPHETS;
      case "Writings":
        return WRITINGS;
    }
    throw new IllegalArgumentException(
      "Cannot identify tanakh section: " + name
    );
  }
}
