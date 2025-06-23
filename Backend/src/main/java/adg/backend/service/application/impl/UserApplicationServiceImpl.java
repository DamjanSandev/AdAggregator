package adg.backend.service.application.impl;

import adg.backend.dto.request.LoginUserDto;
import adg.backend.dto.request.RequestUserDto;
import adg.backend.dto.response.LoginResponseDto;
import adg.backend.dto.response.ResponseUserDto;
import adg.backend.helpers.JwtHelper;
import adg.backend.model.domain.User;
import adg.backend.model.exceptions.InvalidArgumentsException;
import adg.backend.service.application.UserApplicationService;
import adg.backend.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<ResponseUserDto> register(RequestUserDto createUserDto) {
        User user = userService.register(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.repeatPassword(),
                createUserDto.name(),
                createUserDto.surname(),
                createUserDto.role()
        );
        return Optional.of(ResponseUserDto.from(user));
    }

    @Override
    public Optional<LoginResponseDto> login(LoginUserDto loginUserDto) {
        User user = userService.login(
                loginUserDto.username(),
                loginUserDto.password()
        );

        String token = jwtHelper.generateToken(user);

        return Optional.of(new LoginResponseDto(token));
    }

    @Override
    public Optional<ResponseUserDto> findByUsername(String username) {
        return Optional.of(ResponseUserDto.from(userService.findByUsername(username).orElseThrow(InvalidArgumentsException::new)));
    }
}