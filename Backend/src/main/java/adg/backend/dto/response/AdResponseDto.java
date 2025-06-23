package adg.backend.dto.response;

import adg.backend.model.domain.Ad;
import adg.backend.model.enumerations.ads.*;

import java.util.Date;
import java.util.List;

public record AdResponseDto(
        String brand,
        String model,
        Integer year,
        FuelType fuelType,
        Integer kilometers,
        TransmissionType transmission,
        BodyType bodyType,
        String color,
        RegistrationType registrationType,
        Date registeredUntil,
        String enginePower,
        EmissionType emissionType,
        String description,
        String photoUrl
) {
    public static AdResponseDto from(Ad ad) {
        return new AdResponseDto(
                ad.getBrand(),
                ad.getModel(),
                ad.getYear(),
                ad.getFuelType(),
                ad.getKilometers(),
                ad.getTransmission(),
                ad.getBodyType(),
                ad.getColor(),
                ad.getRegistrationType(),
                ad.getRegisteredUntil(),
                ad.getEnginePower(),
                ad.getEmissionType(),
                ad.getDescription(),
                ad.getPhotoUrl()
        );
    }

    public static List<AdResponseDto> from(List<Ad> ads) {
        return ads.stream()
                .map(AdResponseDto::from)
                .toList();
    }
}
