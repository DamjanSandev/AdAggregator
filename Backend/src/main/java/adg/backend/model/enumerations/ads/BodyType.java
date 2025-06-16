package adg.backend.model.enumerations.ads;

import java.util.Arrays;

public enum BodyType {

    SMALL_CITY_CAR("Maли градски"),
    HATCHBACK("Хеџбек"),
    SEDAN("Седани"),
    CARAVAN("Каравани"),
    MPV("Моноволумен"),
    SUV("Теренци - SUV"),
    CABRIOLET("Кабриолети"),
    COUPE("Купеа"),
    OTHER("Останато");

    private final String displayNameMk;

    BodyType(String displayNameMk) {
        this.displayNameMk = displayNameMk;
    }

    public static BodyType fromMacedonian(String raw) {
        return Arrays.stream(BodyType.values())
                .filter(ft -> ft.displayNameMk.equalsIgnoreCase(raw.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown body type: " + raw));
    }
}
