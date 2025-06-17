package adg.backend.dto.search;

import adg.backend.model.enumerations.ads.*;
import org.springframework.web.bind.annotation.RequestParam;

public record SearchAdDto(
        String brand,
        String model,
        Integer fromYear,
        Integer toYear,
        FuelType fuelType,
        TransmissionType transmission,
        BodyType bodyType,
        String color,
        RegistrationType registrationType,
        EmissionType emissionType,
        Integer fromKilometers,
        Integer toKilometers,
        String enginePower
) {
}
