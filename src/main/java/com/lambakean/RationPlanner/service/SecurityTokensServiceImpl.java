package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.SecurityTokensDto;
import com.lambakean.RationPlanner.exception.AuthenticationException;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.RefreshTokenWrapperRepository;
import com.lambakean.RationPlanner.security.authentication.AccessTokenWrapper;
import com.lambakean.RationPlanner.security.authentication.JwtTokenProvider;
import com.lambakean.RationPlanner.security.authentication.RefreshTokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class SecurityTokensServiceImpl implements SecurityTokensService {

    @Value("${refreshToken.validityTimeMs}")
    private Long refreshTokenValidityTimeMs;

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenWrapperRepository refreshTokenWrapperRepository;
    private final PrincipalService principalService;

    @Autowired
    public SecurityTokensServiceImpl(JwtTokenProvider jwtTokenProvider,
                                     RefreshTokenWrapperRepository refreshTokenWrapperRepository,
                                     PrincipalService principalService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenWrapperRepository = refreshTokenWrapperRepository;
        this.principalService = principalService;
    }

    @Override
    public RefreshTokenWrapper createRefreshToken(User user) {
        return new RefreshTokenWrapper(
                jwtTokenProvider.createToken(user.getId(), refreshTokenValidityTimeMs),
                user,
                LocalDateTime.now().plus(refreshTokenValidityTimeMs, ChronoUnit.MILLIS)
        );
    }

    @Override
    public AccessTokenWrapper createAccessToken(User user) {
        return new AccessTokenWrapper(
                jwtTokenProvider.createToken(user.getId()),
                user,
                LocalDateTime.now().plus(jwtTokenProvider.getDefaultValidityTimeMs(), ChronoUnit.MILLIS)
        );
    }

    @Override
    public void save(RefreshTokenWrapper refreshTokenWrapper) {
        this.refreshTokenWrapperRepository.save(refreshTokenWrapper);
    }

    @Override
    public SecurityTokensDto updateTokens(String refreshToken) {

        String EXCEPTION_MSG = "Не удалось обновить токены безопасности, войдите в аккаунт заново";

        RefreshTokenWrapper refreshTokenWrapper = refreshTokenWrapperRepository.findByToken(refreshToken).orElseThrow(
                () -> new AuthenticationException(EXCEPTION_MSG)
        );

        if(!refreshTokenWrapper.isTokenValid()) {
            throw new AuthenticationException(EXCEPTION_MSG);
        }

        User user = refreshTokenWrapper.getUser();

        refreshTokenWrapperRepository.delete(refreshTokenWrapper);

        AccessTokenWrapper newAccessTokenWrapper = createAccessToken(user);
        RefreshTokenWrapper newRefreshTokenWrapper = createRefreshToken(user);

        save(newRefreshTokenWrapper);

        return new SecurityTokensDto(newAccessTokenWrapper.getToken(), newRefreshTokenWrapper.getToken());
    }
}
