package living.tanach.api.model.contstants;

public enum Prophets {

    JOSHUA("Joshua"),
    JUDGES_I("Judges 1"),
    JUDGES_II("Judges 2"),
    SAMUEL_I("Samuel 1"),
    SAMUEL_II("Samuel 2"),
    KINGS_I("Kings 1"),
    KINGS_II("Kings 2"),
    ISAIAH("Isaiah"),
    JEREMIAH("Jeremiah"),
    EZEKIEL("Ezekiel"),
    HOSEA("Hosea"),
    JOEL("Joel"),
    AMOS("Amos"),
    OBADIAH("Obadiah"),
    JONAH("Jonah"),
    MICHA("Micha"),
    NAHUM("Nahum"),
    HABAKKUK("Habakkuk"),
    ZEPHANIAH("Zephania"),
    HAGGAI("Haggai"),
    ZECHARIAH("Zechariah"),
    MALACHI("Malachi"),


    private final String name;
    Prophets(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
