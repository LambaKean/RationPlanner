package com.lambakean.RationPlanner.controller;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody UserCredentialsDto userCredentialsDto,
                                            HttpServletRequest httpServletRequest) {

        UserDto userDto = userService.register(userCredentialsDto);

        httpServletRequest
                .getSession(true)
                .setAttribute("userId", userDto.getId());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/session")
    public ResponseEntity<UserDto> login(@RequestBody UserCredentialsDto userCredentialsDto,
                                         HttpServletRequest httpServletRequest) {

        UserDto userDto = userService.login(userCredentialsDto);

        httpServletRequest
                .getSession(true)
                .setAttribute("userId", userDto.getId());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getSession(false) == null) {
            throw new AuthenticationException("Вы должны войти в свой аккаунт, чтобы получить к нему доступ");
        }

        String userId = (String) httpServletRequest.getSession().getAttribute("userId");

        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }
}
