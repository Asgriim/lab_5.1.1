package data;

public enum Climate {
    RAIN_FOREST("rain forest"),
    OCEANIC("oceanic"),
    MEDITERRANIAN("mediterranian"),
    SUBARCTIC("subarctic");

    private String st;

    Climate(String st) {
        this.st = st;
    }

    public String getSt() {
        return st;
    }
}
