package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.model.SecurityTokensHolder;
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
    public SecurityTokensHolder updateTokens(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse) {

        String refreshToken = refreshTokenResolver.resolveToken(httpServletRequest).orElseThrow(
                () -> new AuthenticationException(
                        "Для обновления токенов безопасности в запросе должен присутствовать refresh токен."
                )
        );

        RefreshTokenWrapper refreshTokenWrapper = refreshTokenWrapperRepository.findByToken(refreshToken).orElseThrow(
                () -> new AuthenticationException("Refresh токен невалиден или просрочен. Войдите в аккаунт заново.")
        );

        if(refreshTokenWrapper.isExpired()) {
            throw new AuthenticationException("Refresh токен просрочен. Войдите в аккаунт заново.");
        }

        User user = refreshTokenWrapper.getUser();

        AccessTokenWrapper newAccessTokenWrapper = createAccessTokenWrapper(user);
        RefreshTokenWrapper newRefreshTokenWrapper = createRefreshTokenWrapper(user);

        refreshTokenWrapperRepository.save(refreshTokenWrapper);

        httpServletResponse.addCookie(createRefreshTokenCookie(newRefreshTokenWrapper));

        return new SecurityTokensHolder(user, newAccessTokenWrapper, newRefreshTokenWrapper);
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
