package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.SecurityTokensDto;
import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.dto.UserWithTokensDto;
import com.lambakean.RationPlanner.exception.UserNotLoggedInException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.SecurityTokensService;
import com.lambakean.RationPlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final PrincipalService principalService;
    private final SecurityTokensService securityTokensService;

    @Autowired
    public UserController(UserService userService,
                          PrincipalService principalService,
                          SecurityTokensService securityTokensService) {
        this.userService = userService;
        this.principalService = principalService;
        this.securityTokensService = securityTokensService;
    }

    @PostMapping
    public ResponseEntity<UserWithTokensDto> register(@RequestBody UserCredentialsDto userCredentialsDto,
                                                      HttpServletResponse httpServletResponse) {

        UserWithTokensDto userWithTokensDto = userService.register(userCredentialsDto, httpServletResponse);

        return new ResponseEntity<>(userWithTokensDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokensDto> login(@RequestBody UserCredentialsDto userCredentialsDto,
                                                   HttpServletResponse httpServletResponse) {

        UserWithTokensDto userWithTokensDto = userService.login(userCredentialsDto, httpServletResponse);

        return new ResponseEntity<>(userWithTokensDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<UserDto> getCurrentUser() {

        User user = (User) principalService.getPrincipal().orElseThrow(
                () -> new UserNotLoggedInException("Вы должны войти в аккаунт, чтобы просмотреть свой профиль")
        );

        return new ResponseEntity<>(new UserDto(user.getId(), user.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<SecurityTokensDto> updateTokens(HttpServletRequest httpServletRequest,
                                                          HttpServletResponse httpServletResponse) {

        SecurityTokensDto newSecurityTokensDto = securityTokensService.updateTokens(
                httpServletRequest,
                httpServletResponse
        );

        return new ResponseEntity<>(newSecurityTokensDto, HttpStatus.CREATED);
    }
}
