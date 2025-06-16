package adg.backend.model.domain;

import adg.backend.model.enumerations.InteractionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "interactions",
        indexes = {
                @Index(name = "idx_interactions_user", columnList = "user_username"),
                @Index(name = "idx_interactions_ad", columnList = "ad_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private InteractionType interactionType;

    @Column(nullable = false)
    private int strength;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
