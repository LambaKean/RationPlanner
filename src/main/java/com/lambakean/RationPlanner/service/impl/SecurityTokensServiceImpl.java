package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.dto.SecurityTokensDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.RefreshTokenWrapperRepository;
import com.lambakean.RationPlanner.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.security.JwtTokenProvider;
import com.lambakean.RationPlanner.model.RefreshTokenWrapper;
import com.lambakean.RationPlanner.security.TokenResolver;
import com.lambakean.RationPlanner.service.SecurityTokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SecurityTokensServiceImpl implements SecurityTokensService {

    @Value("${access-token.validity-time-in-minutes}")
    private Long accessTokenValidityTimeInMinutes;

    @Value("${refresh-token.validity-time-in-minutes}")
    private Long refreshTokenValidityTimeInMinutes;

    private final RefreshTokenWrapperRepository refreshTokenWrapperRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenResolver refreshTokenResolver;

    @Autowired
    public SecurityTokensServiceImpl(RefreshTokenWrapperRepository refreshTokenWrapperRepository,
                                     JwtTokenProvider jwtTokenProvider,
                                     TokenResolver refreshTokenResolver) {
        this.refreshTokenWrapperRepository = refreshTokenWrapperRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenResolver = refreshTokenResolver;
    }

    @Override
    public RefreshTokenWrapper createRefreshTokenWrapper(@NonNull User user) {

        String refreshToken = jwtTokenProvider.createToken(user, refreshTokenValidityTimeInMinutes);
        ZonedDateTime expiresAt = ZonedDateTime.now().plusMinutes(refreshTokenValidityTimeInMinutes);

        return new RefreshTokenWrapper(refreshToken, user, expiresAt);
    }

    @Override
    public AccessTokenWrapper createAccessTokenWrapper(@NonNull User user) {

        String accessToken = jwtTokenProvider.createToken(user, accessTokenValidityTimeInMinutes);
        ZonedDateTime expiresAt = ZonedDateTime.now().plusMinutes(accessTokenValidityTimeInMinutes);

        return new AccessTokenWrapper(accessToken, user, expiresAt);
    }

    @Override
    public void save(@NonNull RefreshTokenWrapper refreshTokenWrapper) {
        refreshTokenWrapperRepository.save(refreshTokenWrapper);
    }

    @Override
    public SecurityTokensDto updateTokens(HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse) {  // todo refactor

        String EXCEPTION_MSG = "Не удалось обновить токены безопасности, войдите в аккаунт заново";

        String refreshToken = refreshTokenResolver.resolveToken(httpServletRequest).orElseThrow(
                () -> new AuthenticationException(EXCEPTION_MSG)
        );

        RefreshTokenWrapper refreshTokenWrapper = refreshTokenWrapperRepository.findByToken(refreshToken).orElseThrow(
                () -> new AuthenticationException(EXCEPTION_MSG)
        );

        if(refreshTokenWrapper.isExpired()) {
            throw new AuthenticationException(EXCEPTION_MSG);
        }

        User user = refreshTokenWrapper.getUser();

        refreshTokenWrapperRepository.delete(refreshTokenWrapper);

        AccessTokenWrapper newAccessTokenWrapper = createAccessTokenWrapper(user);
        RefreshTokenWrapper newRefreshTokenWrapper = createRefreshTokenWrapper(user);

        save(newRefreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(newRefreshTokenWrapper));

        return new SecurityTokensDto(newAccessTokenWrapper.getToken(), newRefreshTokenWrapper.getToken());
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
