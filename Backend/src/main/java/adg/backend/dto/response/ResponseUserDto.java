package adg.backend.dto.response;

import adg.backend.model.domain.User;
import adg.backend.model.enumerations.Role;

public record ResponseUserDto(String username, String name, String surname, Role role) {

    public static ResponseUserDto from(User user) {
        return new ResponseUserDto(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }

    public User toUser() {
        return new User(username, name, surname, role);
    }
}