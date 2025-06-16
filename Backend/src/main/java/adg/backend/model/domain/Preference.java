package adg.backend.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(Preference.PK.class)
public class Preference {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Column(nullable = false)
    private Integer rank;

    @Column(name = "generated_at", nullable = false, updatable = false)
    private Instant generatedAt = Instant.now();

    @Data
    @NoArgsConstructor
    public static class PK implements Serializable {
        private String user;
        private Long ad;
        @Serial
        private static final long serialVersionUID = 1L;
    }
}
