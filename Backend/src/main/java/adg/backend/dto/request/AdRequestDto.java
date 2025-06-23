package adg.backend.dto.request;

import adg.backend.model.domain.Ad;
import adg.backend.model.enumerations.ads.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

public record AdRequestDto(
        String url,
        String brand,
        String model,
        Integer year,
        FuelType fuelType,
        Integer kilometers,
        TransmissionType transmission,
        BodyType bodyType,
        String color,
        RegistrationType registrationType,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date registeredUntil,
        String enginePower,
        EmissionType emissionType,
        String description,
        String photoUrl
) {
    public Ad toAd() {
        return new Ad(url, brand, model, year,
                fuelType, kilometers, transmission, bodyType,
                color, registrationType, registeredUntil,
                enginePower, emissionType, description, photoUrl);
    }
}
