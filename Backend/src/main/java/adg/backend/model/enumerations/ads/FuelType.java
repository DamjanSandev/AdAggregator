package adg.backend.model.enumerations.ads;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FuelType {

    DIESEL("Дизел"),
    PETROL("Бензин"),
    PETROL_LPG("Бензин / Плин"),
    HYBRID_DIESEL_ELECTRIC("Хибрид (Дизел / Електро)"),
    HYBRID_PETROL_ELECTRIC("Хибрид (Бензин / Електро)"),
    ELECTRIC("Електричен автомобил");

    private final String displayNameMk;

    FuelType(String displayNameMk) {
        this.displayNameMk = displayNameMk;
    }

    public static FuelType fromMacedonian(String raw) {
        return Arrays.stream(FuelType.values())
                .filter(ft -> ft.displayNameMk.equalsIgnoreCase(raw.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown fuel type: " + raw));
    }
}
