package adg.backend.service.application.impl;

import adg.backend.dto.response.ModelResponseDto;
import adg.backend.model.domain.Brand;
import adg.backend.service.application.ModelApplicationService;
import adg.backend.service.domain.BrandService;
import adg.backend.service.domain.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelApplicationServiceImpl implements ModelApplicationService {
    private final ModelService modelService;
    private final BrandService brandService;

    public ModelApplicationServiceImpl(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @Override
    public List<ModelResponseDto> findByBrand(String name) {
        Optional<Brand> brand = brandService.findByName(name);
        return brand.map(value -> modelService.findByBrand(value).stream().map(ModelResponseDto::from).collect(Collectors.toList())).orElseGet(List::of);
    }
}
