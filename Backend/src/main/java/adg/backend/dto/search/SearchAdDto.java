package adg.backend.dto.search;

import adg.backend.model.enumerations.ads.*;

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
        String enginePower,
        Integer fromPrice,
        Integer toPrice
) {
}
