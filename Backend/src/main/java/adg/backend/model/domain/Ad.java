package adg.backend.model.domain;

import adg.backend.model.enumerations.ads.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    private Integer kilometers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransmissionType transmission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationType registrationType;

    private Date registeredUntil;

    private String enginePower;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmissionType emissionType;

    private String description;

    private String photoUrl;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();
}
