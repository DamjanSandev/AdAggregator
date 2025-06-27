package adg.backend.repository;

import adg.backend.model.domain.Model;
import adg.backend.model.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByBrand(Brand brand);
}
