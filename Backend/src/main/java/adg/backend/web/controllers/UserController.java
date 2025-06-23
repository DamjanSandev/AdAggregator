package adg.backend.web.controllers;

import adg.backend.dto.request.LoginUserDto;
import adg.backend.dto.request.RequestUserDto;
import adg.backend.dto.response.LoginResponseDto;
import adg.backend.dto.response.ResponseUserDto;
import adg.backend.model.exceptions.InvalidArgumentsException;
import adg.backend.model.exceptions.InvalidUserCredentialsException;
import adg.backend.model.exceptions.PasswordsDoNotMatchException;
import adg.backend.service.application.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody RequestUserDto requestUserDto) {
        try {
            return userApplicationService.register(requestUserDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            return userApplicationService.login(loginUserDto)
                    .map(ResponseEntity::ok)
                    .orElseThrow(InvalidUserCredentialsException::new);
        } catch (InvalidUserCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
