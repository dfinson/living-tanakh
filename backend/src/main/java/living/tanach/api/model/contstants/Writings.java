package living.tanach.api.model.contstants;

public enum Writings {
    PSALMS("Psalms"),
    PROVERBS("Proverbs"),
    JOB("Job"),
    RUTH("Ruth"),
    LAMENTATIONS("Lamentations"),
    ECCLESIASTES("Ecclesiastes"),
    ESTHER("Esther");

    private final String name;
    Writings(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
