package adg.backend.dto.request;

import java.util.List;

public record PreferenceRequestBulkDto(String user, List<Long> adIds) {
}
