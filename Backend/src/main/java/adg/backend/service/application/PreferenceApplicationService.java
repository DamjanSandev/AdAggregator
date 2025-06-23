package adg.backend.service.application;

import adg.backend.dto.request.PreferenceRequestBulkDto;
import adg.backend.dto.response.AdResponseDto;

import java.util.List;

public interface PreferenceApplicationService {
    void bulkWithNewPreferences(PreferenceRequestBulkDto preferenceBulkDto);

    List<AdResponseDto> getUserPreferenceAds(String username);
}
