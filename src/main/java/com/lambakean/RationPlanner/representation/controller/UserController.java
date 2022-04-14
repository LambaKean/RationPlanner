package com.lambakean.rationplanner.representation.controller;

import com.lambakean.rationplanner.data.model.SecurityTokensHolder;
import com.lambakean.rationplanner.data.model.User;
import com.lambakean.rationplanner.domain.mapper.SecurityTokensHolderMapper;
import com.lambakean.rationplanner.domain.mapper.UserMapper;
import com.lambakean.rationplanner.domain.service.PrincipalService;
import com.lambakean.rationplanner.domain.service.SecurityTokensService;
import com.lambakean.rationplanner.domain.service.UserService;
import com.lambakean.rationplanner.representation.dto.SecurityTokensHolderDto;
import com.lambakean.rationplanner.representation.dto.UserDto;
import com.lambakean.rationplanner.representation.dto.form.UserAuthenticationForm;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PrincipalService principalService;
    private final SecurityTokensService securityTokensService;
    private final UserMapper userMapper;
    private final SecurityTokensHolderMapper securityTokensHolderMapper;

    @ApiOperation(
            value = "Регистрация пользователя",
            notes = "При регистрации пользователя также создаётся cookie с refresh токеном."
    )
    @PostMapping
    public ResponseEntity<SecurityTokensHolderDto> register(@RequestBody UserAuthenticationForm userAuthenticationForm,
                                                            HttpServletResponse httpServletResponse) {

        SecurityTokensHolder securityTokensHolder = userService.register(
                userMapper.toUser(userAuthenticationForm),
                httpServletResponse
        );

        SecurityTokensHolderDto securityTokensHolderDto =
                securityTokensHolderMapper.toSecurityTokensHolderDto(securityTokensHolder);

        return new ResponseEntity<>(securityTokensHolderDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Вход пользователя в аккаунт",
            notes = "При входе пользователя в аккаунт также создаётся cookie с refresh токеном."
    )
    @PostMapping("/login")
    public ResponseEntity<SecurityTokensHolderDto> login(@RequestBody UserAuthenticationForm userAuthenticationForm,
                                                         HttpServletResponse httpServletResponse) {

        SecurityTokensHolder securityTokensHolder = userService.login(
                userMapper.toUser(userAuthenticationForm),
                httpServletResponse
        );

        SecurityTokensHolderDto securityTokensHolderDto =
                securityTokensHolderMapper.toSecurityTokensHolderDto(securityTokensHolder);

        return ResponseEntity.ok(securityTokensHolderDto);
    }

    @ApiOperation("Получение информации о залогиненном пользователе")
    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {

        User user = principalService.getPrincipal();
        UserDto userDto = userMapper.toUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @ApiOperation(
            value = "Обновление токенов безопасности",
            notes = "В запросе должен зодержаться cookie файл с валидным refresh токеном." +
                    " При обновлении токенов создаётся новый cookie с refresh токеном."
    )
    @PostMapping("/token")
    public ResponseEntity<SecurityTokensHolderDto> updateTokens(HttpServletRequest httpServletRequest,
                                                                HttpServletResponse httpServletResponse) {

        SecurityTokensHolder securityTokensHolder = securityTokensService.updateTokens(
                httpServletRequest,
                httpServletResponse
        );

        SecurityTokensHolderDto securityTokensHolderDto =
                securityTokensHolderMapper.toSecurityTokensHolderDto(securityTokensHolder);

        return ResponseEntity.ok(securityTokensHolderDto);
    }
}
