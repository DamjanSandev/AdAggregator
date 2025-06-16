package adg.backend.model.enumerations.ads;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransmissionType {

    MANUAL("Рачен"),
    AUTOMATIC("Автоматски"),
    SEMI_AUTOMATIC("Полуавтоматски");

    private final String displayNameMk;

    TransmissionType(String displayNameMk) {
        this.displayNameMk = displayNameMk;
    }

    public static TransmissionType fromMacedonian(String raw) {
        return Arrays.stream(values())
                .filter(t -> t.displayNameMk.equalsIgnoreCase(raw.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transmission type: " + raw));
    }
}
