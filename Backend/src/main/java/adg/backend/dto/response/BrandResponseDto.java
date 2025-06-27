package adg.backend.dto.response;

import adg.backend.model.domain.Brand;

public record BrandResponseDto(
        Long id,
        String name
) {

    public static BrandResponseDto from(Brand brand) {
        return new BrandResponseDto(brand.getId(), brand.getName());
    }
}
