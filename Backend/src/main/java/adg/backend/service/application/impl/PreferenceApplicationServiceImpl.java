package adg.backend.service.application.impl;

import adg.backend.dto.request.PreferenceRequestBulkDto;
import adg.backend.model.domain.Ad;
import adg.backend.model.domain.User;
import adg.backend.model.exceptions.UserNotFoundException;
import adg.backend.service.application.PreferenceApplicationService;
import adg.backend.service.domain.AdService;
import adg.backend.service.domain.PreferenceService;
import adg.backend.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenceApplicationServiceImpl implements PreferenceApplicationService {

    private final PreferenceService preferenceService;
    private final UserService userService;
    private final AdService adService;

    public PreferenceApplicationServiceImpl(PreferenceService preferenceService, UserService userService,
                                            AdService adService) {
        this.preferenceService = preferenceService;
        this.userService = userService;
        this.adService = adService;
    }

    @Override
    public void bulkWithNewPreferences(PreferenceRequestBulkDto preferenceBulkDto) {
        User user = userService.findByUsername(preferenceBulkDto.user())
                .orElseThrow(() -> new UserNotFoundException(preferenceBulkDto.user()));

        List<Ad> adds = preferenceBulkDto.adIds().stream()
                .map(adService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        this.preferenceService.bulkWithNewPreferences(user, adds);
    }
}
