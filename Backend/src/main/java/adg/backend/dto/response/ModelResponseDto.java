package adg.backend.dto.response;

import adg.backend.model.domain.Model;

public record ModelResponseDto(
        Long id,
        String name,
        Long brandId
) {
    public static ModelResponseDto from(Model model) {
        return new ModelResponseDto(
                model.getId(),
                model.getName(),
                model.getBrand().getId()
        );
    }
}
