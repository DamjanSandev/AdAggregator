package adg.backend.service.application;

import adg.backend.dto.request.AdRequestDto;
import adg.backend.dto.response.AdResponseDto;
import adg.backend.dto.search.SearchAdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdApplicationService {

    List<AdResponseDto> findAll(SearchAdDto searchRequest);

    Page<AdResponseDto> findAll(SearchAdDto searchRequest, Pageable pageable);

    Optional<AdResponseDto> findById(Long id);

    Optional<AdResponseDto> save(AdRequestDto ad);

    Optional<AdResponseDto> update(Long id, AdRequestDto Ad);

    Optional<AdResponseDto> deleteById(Long id);
}
