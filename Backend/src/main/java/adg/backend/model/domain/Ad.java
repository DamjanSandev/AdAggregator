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

    @Column(nullable = false, length = 512)
    private String url;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private Integer kilometers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransmissionType transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    private BodyType bodyType;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", nullable = false)
    private RegistrationType registrationType;

    @Column(name = "registered_until", nullable = false)
    private Date registeredUntil;

    @Column(name = "engine_power", nullable = false)
    private String enginePower;

    @Enumerated(EnumType.STRING)
    @Column(name = "emission_type", nullable = false)
    private EmissionType emissionType;

    @Column(length = 2048)
    private String description;

    @Column(name = "photo_url", nullable = false, length = 512)
    private String photoUrl;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    public Ad(String url, String brand, String model, Integer year, FuelType fuelType, Integer kilometers, TransmissionType transmission, BodyType bodyType, String color, RegistrationType registrationType, Date registeredUntil, String enginePower, EmissionType emissionType, String description, String photoUrl) {
        this.url = url;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.kilometers = kilometers;
        this.transmission = transmission;
        this.bodyType = bodyType;
        this.color = color;
        this.registrationType = registrationType;
        this.registeredUntil = registeredUntil;
        this.enginePower = enginePower;
        this.emissionType = emissionType;
        this.description = description;
        this.photoUrl = photoUrl;
    }
}
