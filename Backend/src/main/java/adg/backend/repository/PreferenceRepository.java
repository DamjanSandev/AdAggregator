package adg.backend.repository;

import adg.backend.model.domain.Preference;
import adg.backend.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Preference.PK> {
    void deleteAllByUser(User user);

    List<Preference> findAllByUser(User user);
}
