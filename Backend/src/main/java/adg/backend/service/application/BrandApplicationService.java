package adg.backend.service.application;

import adg.backend.dto.response.BrandResponseDto;
import adg.backend.model.domain.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandApplicationService {
    List<BrandResponseDto> findAll();

    Optional<BrandResponseDto> findByName(String name);
}
