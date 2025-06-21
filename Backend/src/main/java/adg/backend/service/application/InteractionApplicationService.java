package adg.backend.service.application;

import adg.backend.dto.request.InteractionRequestDTO;
import adg.backend.dto.response.InteractionResponseDto;


import java.util.List;
import java.util.Optional;

public interface InteractionApplicationService {
    Optional<InteractionResponseDto> findById(Long id);

    List<InteractionResponseDto> findAllByUser(String userName);

    List<InteractionResponseDto> findByUserAndAd(String userName, Long adId);

    Optional<InteractionResponseDto> save(InteractionRequestDTO interaction);

    Optional<InteractionResponseDto> deleteById(Long id);
}
