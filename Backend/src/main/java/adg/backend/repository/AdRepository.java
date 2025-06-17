package adg.backend.repository;

import adg.backend.model.domain.Ad;
import adg.backend.repository.specification.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaSpecificationRepository<Ad, Long> {

}
