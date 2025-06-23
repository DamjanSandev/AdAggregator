package adg.backend.service.application;

import adg.backend.dto.request.LoginUserDto;
import adg.backend.dto.request.RequestUserDto;
import adg.backend.dto.response.LoginResponseDto;
import adg.backend.dto.response.ResponseUserDto;


import java.util.Optional;

public interface UserApplicationService {

    Optional<ResponseUserDto> register(RequestUserDto createUserDto);

    Optional<LoginResponseDto> login(LoginUserDto loginUserDto);

    Optional<ResponseUserDto> findByUsername(String username);
}
