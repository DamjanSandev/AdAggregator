package adg.backend.dto.request;

import adg.backend.model.domain.User;
import adg.backend.model.enumerations.Role;

public record RequestUserDto(
        String username,
        String password,
        String repeatPassword,
        String name,
        String surname,
        Role role
) {

    public User toUser() {
        return new User(username, password, name, surname, role);
    }
}