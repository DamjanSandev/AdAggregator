package adg.backend.service.domain;

import adg.backend.model.domain.User;
import adg.backend.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);

    User login(String username, String password);

    Optional<User> findByUsername(String username);
}