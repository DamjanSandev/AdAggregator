package adg.backend.service.application;

import adg.backend.dto.request.PreferenceRequestBulkDto;


public interface PreferenceApplicationService {
    void bulkWithNewPreferences(PreferenceRequestBulkDto preferenceBulkDto);
}
