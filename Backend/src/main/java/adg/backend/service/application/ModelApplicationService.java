package adg.backend.service.application;

import adg.backend.dto.response.ModelResponseDto;
import adg.backend.model.domain.Brand;
import adg.backend.model.domain.Model;

import java.util.List;

public interface ModelApplicationService {
    List<ModelResponseDto> findByBrand(String name);

}
