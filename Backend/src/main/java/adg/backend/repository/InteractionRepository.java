package adg.backend.repository;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;
import adg.backend.model.enumerations.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    @Query("SELECT i FROM Interaction i WHERE i.user = :user AND i.ad = :ad AND i.interaction_type = :interactionType ORDER BY i.createdAt ASC LIMIT 1")
    Optional<Interaction> findByUserAndAdAndInteraction(User user, Ad ad, InteractionType interactionType);

    @Query("SELECT i FROM Interaction i WHERE i.user = :user AND i.interaction_type = :interactionType ORDER BY i.createdAt ASC")
    List<Interaction> findAllByUserAndInteraction(User user, InteractionType interactionType);

    @Query("select i from Interaction i where i.interaction_type = 'VIEW' and i.createdAt >= :since")
    List<Interaction> findViewsSince(Instant since);
}
