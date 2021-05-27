package org.maayan.maayanproject.model.contstants;

public enum Torah {
  GENESIS("Genesis"),
  EXODUS("Exodus"),
  LEVITICUS("Leviticus"),
  NUMBERS("Numbers"),
  DEUTERONOMY("Deuteronomy");

  private final String name;

  Torah(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
