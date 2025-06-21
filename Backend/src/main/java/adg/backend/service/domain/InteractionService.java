package adg.backend.service.domain;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;


import java.util.List;
import java.util.Optional;

public interface InteractionService {

    Optional<Interaction> findById(Long id);

    List<Interaction> findAllByUser(User user);

    List<Interaction> findByUserAndAd(User user, Ad ad);

    Optional<Interaction> save(Interaction interaction);

    Optional<Interaction> deleteById(Long id);
}
