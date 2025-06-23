package adg.backend.service.domain.impl;

import adg.backend.model.domain.Ad;
import adg.backend.model.exceptions.AdNotFoundException;
import adg.backend.model.exceptions.InvalidAdException;
import adg.backend.repository.AdRepository;
import adg.backend.service.domain.AdService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private static Integer firstCarYear = 1945;

    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    private boolean validateAd(Ad ad) {
        return ad.getFuelType() != null
                && !ad.getFuelType().name().isEmpty()
                && ad.getTransmission() != null
                && !ad.getTransmission().name().isEmpty()
                && ad.getBodyType() != null
                && !ad.getBodyType().name().isEmpty()
                && ad.getRegistrationType() != null
                && !ad.getRegistrationType().name().isEmpty()
                && ad.getEmissionType() != null
                && !ad.getEmissionType().name().isEmpty()
                && ad.getBrand() != null
                && !ad.getBrand().isEmpty()
                && ad.getModel() != null
                && !ad.getModel().isEmpty()
                && ad.getYear() != null
                && ad.getYear() > firstCarYear
                && ad.getKilometers() != null
                && ad.getKilometers() >= 0
                && ad.getColor() != null
                && !ad.getColor().isEmpty()
                && ad.getRegisteredUntil() != null
                && !ad.getRegisteredUntil().toString().isEmpty()
                && ad.getEnginePower() != null
                && !ad.getEnginePower().isEmpty();
    }

    @Override
    public List<Ad> findAll(Specification<Ad> filter) {
        return this.adRepository.findAll(filter);
    }

    @Override
    public Page<Ad> findAll(Specification<Ad> filter, Pageable pageable) {
        return this.adRepository.findAll(filter, pageable);
    }

    @Override
    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }


    @Override
    public Optional<Ad> save(Ad ad) {
        if (!validateAd(ad)) {
            throw new InvalidAdException();
        }
        return Optional.of(adRepository.save(ad));
    }

    @Override
    public Optional<Ad> update(Long id, Ad ad) {
        return adRepository.findById(id).map(existingAd -> {
            if (!validateAd(ad)) {
                throw new InvalidAdException();
            }
            existingAd.setBrand(ad.getBrand());
            existingAd.setModel(ad.getModel());
            existingAd.setYear(ad.getYear());
            existingAd.setFuelType(ad.getFuelType());
            existingAd.setKilometers(ad.getKilometers());
            existingAd.setTransmission(ad.getTransmission());
            existingAd.setBodyType(ad.getBodyType());
            existingAd.setColor(ad.getColor());
            existingAd.setRegistrationType(ad.getRegistrationType());
            existingAd.setRegisteredUntil(ad.getRegisteredUntil());
            existingAd.setEnginePower(ad.getEnginePower());
            existingAd.setEmissionType(ad.getEmissionType());
            existingAd.setDescription(ad.getDescription() != null ? ad.getDescription() : null);
            existingAd.setPhotoUrl(ad.getPhotoUrl());
            return adRepository.save(existingAd);
        });
    }

    @Override
    public Optional<Ad> deleteById(Long id) {
        return adRepository.findById(id).map(ad -> {
            adRepository.delete(ad);
            return ad;
        });
    }
}
