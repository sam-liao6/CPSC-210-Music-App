package ui;

public enum ButtonNames {
    SAVE("Save"),
    SEARCH("Search");

    private final String name;

    // EFFECTS: Creates a button name with given button name
    ButtonNames(String name) {
        this.name = name;
    }

    // EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }
}
