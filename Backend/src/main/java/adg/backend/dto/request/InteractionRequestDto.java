package adg.backend.dto.request;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;
import adg.backend.model.enumerations.InteractionType;

public record InteractionRequestDto(
        String userUsername,
        Long adId,
        InteractionType interactionType
) {
    public Interaction toInteraction(User user, Ad ad) {
        return new Interaction(user, ad, interactionType);
    }
}
