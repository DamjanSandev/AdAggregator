package adg.backend.service.application.impl;

import adg.backend.dto.response.BrandResponseDto;
import adg.backend.service.application.BrandApplicationService;
import adg.backend.service.domain.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandApplicationServiceImpl implements BrandApplicationService {
    private final BrandService brandService;

    public BrandApplicationServiceImpl(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public List<BrandResponseDto> findAll() {
        return brandService.findAll().stream().map(BrandResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public Optional<BrandResponseDto> findByName(String name) {
        return brandService.findByName(name).map(BrandResponseDto::from);
    }
}
