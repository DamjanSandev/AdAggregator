package adg.backend.web.controllers;

import adg.backend.dto.request.PreferenceRequestBulkDto;
import adg.backend.service.application.PreferenceApplicationService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceApplicationService preferenceApplicationService;

    public PreferenceController(PreferenceApplicationService preferenceApplicationService) {
        this.preferenceApplicationService = preferenceApplicationService;
    }

    @PostMapping("/bulk")
    @Transactional
    public void bulk(@RequestBody PreferenceRequestBulkDto preferenceBulkDto) {
        this.preferenceApplicationService.bulkWithNewPreferences(preferenceBulkDto);
    }
}
