package adg.backend.dto.response;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.enumerations.InteractionType;

import java.util.List;

public record InteractionResponseDto(
        ResponseUserDto user,
        AdResponseDto ad,
        InteractionType interactionType,
        int strength
) {
    public static InteractionResponseDto from(Interaction interaction) {
        return new InteractionResponseDto(
                ResponseUserDto.from(interaction.getUser()),
                AdResponseDto.from(interaction.getAd()),
                interaction.getInteraction_type(),
                interaction.getStrength()
        );
    }

    public static List<InteractionResponseDto> from(List<Interaction> interactions) {
        return interactions.stream()
                .map(InteractionResponseDto::from)
                .toList();
    }
}
