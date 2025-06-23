package adg.backend.service.domain;


import adg.backend.model.domain.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface AdService {
    List<Ad> findAll(Specification<Ad> filter);

    Page<Ad> findAll(Specification<Ad> filter, Pageable pageable);

    Optional<Ad> findById(Long id);

    Optional<Ad> save(Ad ad);

    Optional<Ad> update(Long id, Ad Ad);

    Optional<Ad> deleteById(Long id);

}
