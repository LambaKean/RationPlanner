package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.UserCredentialsDto;
import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.dto.UserWithTokensDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.UserRepository;
import com.lambakean.RationPlanner.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.model.RefreshTokenWrapper;
import com.lambakean.RationPlanner.service.SecurityTokensService;
import com.lambakean.RationPlanner.service.UserService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.UserUniquenessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator userValidator;
    private final UserUniquenessValidator userUniquenessValidator;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final SecurityTokensService securityTokensService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Validator userValidator,
                           UserUniquenessValidator userUniquenessValidator,
                           @Qualifier("bCryptPasswordEncoder") PasswordEncoder passwordEncoder,
                           ValidationService validationService,
                           SecurityTokensService securityTokensService) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.userUniquenessValidator = userUniquenessValidator;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.securityTokensService = securityTokensService;
    }

    @Override
    public UserWithTokensDto register(@NonNull UserCredentialsDto userCredentialsDto,
                                      HttpServletResponse httpServletResponse) {

        String username = userCredentialsDto.getUsername();
        String password = userCredentialsDto.getPassword();

        User user = new User(username, password);

        validationService.validateThrowExceptionIfInvalid(user, userValidator, userUniquenessValidator);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.saveAndFlush(user);

        UserDto userDto = new UserDto(user.getId(), user.getUsername());

        AccessTokenWrapper accessTokenWrapper = securityTokensService.createAccessTokenWrapper(user);
        RefreshTokenWrapper refreshTokenWrapper = securityTokensService.createRefreshTokenWrapper(user);

        securityTokensService.save(refreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(refreshTokenWrapper));

        return new UserWithTokensDto(userDto, accessTokenWrapper.getToken(), refreshTokenWrapper.getToken());
    }

    @Override
    public UserWithTokensDto login(@NonNull UserCredentialsDto userCredentialsDto,
                                   HttpServletResponse httpServletResponse) {

        String username = userCredentialsDto.getUsername();
        String password = userCredentialsDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AuthenticationException("Пользователя с такими логином и паролем не существует")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Пользователя с такими логином и паролем не существует");
        }


        UserDto userDto = new UserDto(user.getId(), user.getUsername());

        AccessTokenWrapper accessTokenWrapper = securityTokensService.createAccessTokenWrapper(user);
        RefreshTokenWrapper refreshTokenWrapper = securityTokensService.createRefreshTokenWrapper(user);

        securityTokensService.save(refreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(refreshTokenWrapper));

        return new UserWithTokensDto(userDto, accessTokenWrapper.getToken(), refreshTokenWrapper.getToken());
    }

    @Override
    public UserDto findUserById(@NonNull String id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Пользователь с id \"%s\" не найден", id))
        );

        return new UserDto(user.getId(), user.getUsername());
    }

    @Override
    public Optional<User> findById(@NonNull String id) {
        return userRepository.findById(id);
    }

    private Cookie createRefreshTokenCookie(RefreshTokenWrapper refreshTokenWrapper) {

        long maxAgeInSeconds = ChronoUnit.SECONDS.between(ZonedDateTime.now(), refreshTokenWrapper.getExpiresAt());

        Cookie cookie = new Cookie("refresh_token", refreshTokenWrapper.getToken());
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/user/token");
        cookie.setMaxAge((int) maxAgeInSeconds);

        return cookie;
    }
}