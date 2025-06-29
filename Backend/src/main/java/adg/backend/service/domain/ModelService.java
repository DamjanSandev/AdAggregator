package adg.backend.service.domain;

import adg.backend.model.domain.Brand;
import adg.backend.model.domain.Model;

import java.util.List;

public interface ModelService {
    List<Model> findByBrand(Brand brand);
}
