package adg.backend.service.domain;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.User;

import java.util.List;

public interface PreferenceService {
    void bulkWithNewPreferences(User user, List<Ad> adds);
}
