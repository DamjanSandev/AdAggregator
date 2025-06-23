package adg.backend.service.application;

import adg.backend.dto.request.InteractionRequestDto;
import adg.backend.dto.response.InteractionResponseDto;
import adg.backend.model.enumerations.InteractionType;


import java.util.List;
import java.util.Optional;

public interface InteractionApplicationService {
    Optional<InteractionResponseDto> findById(Long id);

    List<InteractionResponseDto> findAllByUserAndInteraction(String userName, InteractionType interactionType);

    Optional<InteractionResponseDto> findByUserAndAdAndInteraction(String userName, Long adId,InteractionType interactionType);

    Optional<InteractionResponseDto> save(InteractionRequestDto interaction);

    Optional<InteractionResponseDto> deleteById(Long id);
}
