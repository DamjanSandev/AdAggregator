package adg.backend.model.enumerations.ads;

import java.util.Arrays;

public enum EmissionType {

    EURO1("Еуро 1"),
    EURO2("Еуро 2"),
    EURO3("Еуро 3"),
    EURO4("Еуро 4"),
    EURO5("Еуро 5"),
    EURO6("Еуро 6"),
    OTHER("Останато");

    private final String displayNameMk;

    EmissionType(String displayNameMk) {
        this.displayNameMk = displayNameMk;
    }

    public static EmissionType fromMacedonian(String raw) {
        return Arrays.stream(EmissionType.values())
                .filter(ft -> ft.displayNameMk.equalsIgnoreCase(raw.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown emission type: " + raw));
    }
}
