    package adg.backend.web.controllers;

    import adg.backend.dto.request.PreferenceRequestBulkDto;
    import adg.backend.dto.response.AdResponseDto;
    import adg.backend.service.application.PreferenceApplicationService;
    import jakarta.transaction.Transactional;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/preferences")
    public class PreferenceController {

        private final PreferenceApplicationService preferenceApplicationService;

        public PreferenceController(PreferenceApplicationService preferenceApplicationService) {
            this.preferenceApplicationService = preferenceApplicationService;
        }

        @GetMapping("/{username}")
        public ResponseEntity<List<AdResponseDto>> getUserPreferences(
    //            @AuthenticationPrincipal User user,
                @PathVariable String username) {
            return ResponseEntity.ok(this.preferenceApplicationService.getUserPreferenceAds(username));

        }

        @PostMapping("/bulk")
        @Transactional
        public void bulk(@RequestBody PreferenceRequestBulkDto preferenceBulkDto) {
            this.preferenceApplicationService.bulkWithNewPreferences(preferenceBulkDto);
        }
    }
