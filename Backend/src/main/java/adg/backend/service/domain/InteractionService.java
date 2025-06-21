package adg.backend.service.domain;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;
import adg.backend.model.enumerations.InteractionType;


import java.util.List;
import java.util.Optional;

public interface InteractionService {

    Optional<Interaction> findById(Long id);

    List<Interaction> findAllByUserAndInteraction(User user, InteractionType interactionType);

    Optional<Interaction> findByUserAndAdAndInteraction(User user, Ad ad, InteractionType interactionType);

    Optional<Interaction> save(Interaction interaction);

    Optional<Interaction> deleteById(Long id);
}
