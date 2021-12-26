package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.dto.UserWithTokensDto;
import com.lambakean.RationPlanner.exception.UserNotLoggedInException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final PrincipalService principalService;

    @Autowired
    public UserController(UserService userService, PrincipalService principalService) {
        this.userService = userService;
        this.principalService = principalService;
    }

    @PostMapping
    public ResponseEntity<UserWithTokensDto> register(@RequestBody UserCredentialsDto userCredentialsDto) {

        UserWithTokensDto userWithTokensDto = userService.register(userCredentialsDto);

        return new ResponseEntity<>(userWithTokensDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokensDto> login(@RequestBody UserCredentialsDto userCredentialsDto) {

        UserWithTokensDto userWithTokensDto = userService.login(userCredentialsDto);

        return new ResponseEntity<>(userWithTokensDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<UserDto> getCurrentUser() {

        User user = (User) principalService.getCurrentPrincipal().orElseThrow(
                () -> new UserNotLoggedInException("Вы должны войти в аккаунт, чтобы просмотреть свой профиль")
        );

        return new ResponseEntity<>(new UserDto(user.getId(), user.getUsername()), HttpStatus.OK);
    }
}
