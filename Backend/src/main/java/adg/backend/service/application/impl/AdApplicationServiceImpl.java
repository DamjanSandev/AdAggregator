package adg.backend.service.application.impl;

import adg.backend.dto.request.AdRequestDto;
import adg.backend.dto.response.AdResponseDto;
import adg.backend.dto.search.SearchAdDto;
import adg.backend.model.domain.Ad;
import adg.backend.service.application.AdApplicationService;
import adg.backend.service.domain.AdService;
import adg.backend.service.specifications.builder.AdSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdApplicationServiceImpl implements AdApplicationService {
    private final AdService adService;

    public AdApplicationServiceImpl(AdService adService) {
        this.adService = adService;
    }

    @Override
    public List<AdResponseDto> findAll(SearchAdDto searchRequest) {
        Specification<Ad> specification = AdSpecificationBuilder.build(searchRequest);
        return this.adService.findAll(specification)
                .stream()
                .map(AdResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AdResponseDto> findAll(SearchAdDto searchRequest, Pageable pageable) {
        Specification<Ad> specification = AdSpecificationBuilder.build(searchRequest);
        return this.adService.findAll(specification, pageable)
                .map(AdResponseDto::from);
    }

    @Override
    public Optional<AdResponseDto> findById(Long id) {
        return adService.findById(id).map(AdResponseDto::from);
    }

    @Override
    public Optional<AdResponseDto> save(AdRequestDto ad) {
        return adService.save(ad.toAd()).map(AdResponseDto::from);
    }

    @Override
    public Optional<AdResponseDto> update(Long id, AdRequestDto ad) {
        return adService.update(id, ad.toAd())
                .map(AdResponseDto::from);
    }

    @Override
    public Optional<AdResponseDto> deleteById(Long id) {
        return adService.deleteById(id).map(AdResponseDto::from);
    }
}
