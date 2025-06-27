package adg.backend.service.domain;

import adg.backend.model.domain.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();

    Optional<Brand> findByName(String name);
}
