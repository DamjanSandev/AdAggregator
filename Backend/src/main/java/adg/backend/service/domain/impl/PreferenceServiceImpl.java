package adg.backend.service.domain.impl;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Preference;
import adg.backend.model.domain.User;
import adg.backend.repository.PreferenceRepository;
import adg.backend.service.domain.PreferenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void bulkWithNewPreferences(User user, List<Ad> adds) {
        this.preferenceRepository.deleteAllByUser(user);

        AtomicInteger counter = new AtomicInteger(1);
        adds.forEach(ad -> this.preferenceRepository.save(new Preference(user, ad, counter.getAndIncrement())));
    }

    @Override
    public List<Ad> getUserPreferences(User user) {
        return this.preferenceRepository.findAllByUser(user)
                .stream().map(Preference::getAd).collect(Collectors.toList());
    }
}
