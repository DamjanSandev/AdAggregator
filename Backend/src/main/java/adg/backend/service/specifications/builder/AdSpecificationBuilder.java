package adg.backend.service.specifications.builder;

import adg.backend.dto.search.SearchAdDto;
import adg.backend.model.domain.Ad;
import org.springframework.data.jpa.domain.Specification;

import static adg.backend.service.specifications.fieldFilter.FieldFilterSpecification.*;

public class AdSpecificationBuilder {
    private AdSpecificationBuilder() {
    }

    public static Specification<Ad> build(SearchAdDto searchRequest) {
        Specification<Ad> specification = Specification.where(null);

        if (searchRequest.brand() != null && !searchRequest.brand().isBlank()) {
            specification = specification.and(filterEquals(Ad.class, "brand", searchRequest.brand()));
        }

        if (searchRequest.model() != null) {
            specification = specification.and(filterEquals(Ad.class, "model", searchRequest.model()));
        }

        if (searchRequest.fromYear() != null) {
            specification = specification.and(greaterThanOrEqual(Ad.class, "year", searchRequest.fromYear()));
        }

        if (searchRequest.toYear() != null) {
            specification = specification.and(lessThanOrEqual(Ad.class, "year", searchRequest.toYear()));
        }

        if (searchRequest.fuelType() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "fuelType", searchRequest.fuelType().name()));
        }

        if (searchRequest.transmission() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "transmission", searchRequest.transmission().name()));
        }

        if (searchRequest.registrationType() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "registrationType", searchRequest.registrationType().name()));
        }

        if (searchRequest.bodyType() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "bodyType", searchRequest.bodyType().name()));
        }

        if (searchRequest.color() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "color", searchRequest.color()));
        }

        if (searchRequest.emissionType() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "emissionType", searchRequest.emissionType().name()));
        }

        if (searchRequest.fromKilometers() != null) {
            specification = specification.and(greaterThanOrEqual(Ad.class, "kilometers", searchRequest.fromKilometers()));
        }
        if (searchRequest.toKilometers() != null) {
            specification = specification.and(lessThanOrEqual(Ad.class, "kilometers", searchRequest.fromKilometers()));
        }
        if (searchRequest.enginePower() != null) {
            specification = specification.and(filterEqualsV(Ad.class, "enginePower", searchRequest.enginePower()));
        }

        if (searchRequest.fromPrice() != null) {
            specification = specification.and(greaterThanOrEqual(Ad.class, "price", searchRequest.fromPrice()));
        }

        if (searchRequest.toPrice() != null) {
            specification = specification.and(lessThanOrEqual(Ad.class, "price", searchRequest.toPrice()));
        }

        return specification;
    }
}
