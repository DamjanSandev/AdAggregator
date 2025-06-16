package adg.backend.model.enumerations.ads;

import java.util.Arrays;

public enum RegistrationType {

    MACEDONIAN("Македонска"),
    FOREIGN("Странска");

    private final String displayNameMk;

    RegistrationType(String displayNameMk) {
        this.displayNameMk = displayNameMk;
    }

    public static RegistrationType fromMacedonian(String raw) {
        return Arrays.stream(RegistrationType.values())
                .filter(ft -> ft.displayNameMk.equalsIgnoreCase(raw.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown registration type: " + raw));
    }
}
