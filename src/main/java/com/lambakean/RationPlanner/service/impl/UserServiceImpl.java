package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.UserDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.SecurityTokensHolder;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.UserRepository;
import com.lambakean.RationPlanner.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.model.RefreshTokenWrapper;
import com.lambakean.RationPlanner.service.SecurityTokensService;
import com.lambakean.RationPlanner.service.UserService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.UserUniquenessValidator;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator userValidator;
    private final UserUniquenessValidator userUniquenessValidator;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final SecurityTokensService securityTokensService;

    @Override
    public SecurityTokensHolder register(@NonNull User userData,
                                         HttpServletResponse httpServletResponse) {


        validationService.validateThrowExceptionIfInvalid(userData, userValidator, userUniquenessValidator);

        userData.setPassword(passwordEncoder.encode(userData.getPassword()));

        userRepository.saveAndFlush(userData);

        AccessTokenWrapper accessTokenWrapper = securityTokensService.createAccessTokenWrapper(userData);
        RefreshTokenWrapper refreshTokenWrapper = securityTokensService.createRefreshTokenWrapper(userData);

        securityTokensService.save(refreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(refreshTokenWrapper));

        return new SecurityTokensHolder(userData, accessTokenWrapper, refreshTokenWrapper);
    }

    @Override
    public SecurityTokensHolder login(@NonNull User userData,
                                      HttpServletResponse httpServletResponse) {

        User user = userRepository.findByUsername(userData.getUsername()).orElseThrow(
                () -> new AuthenticationException("Пользователя с такими логином и паролем не существует")
        );

        if(!passwordEncoder.matches(userData.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Пользователя с такими логином и паролем не существует");
        }

        AccessTokenWrapper accessTokenWrapper = securityTokensService.createAccessTokenWrapper(user);
        RefreshTokenWrapper refreshTokenWrapper = securityTokensService.createRefreshTokenWrapper(user);

        securityTokensService.save(refreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(refreshTokenWrapper));

        return new SecurityTokensHolder(user, accessTokenWrapper, refreshTokenWrapper);
    }

    public User findById(@NonNull String id) {

        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Пользователь с id \"%s\" не найден", id))
        );
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