package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.SecurityTokensDto;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.security.authentication.AccessTokenWrapper;
import com.lambakean.RationPlanner.security.authentication.RefreshTokenWrapper;

public interface SecurityTokensService {

    RefreshTokenWrapper createRefreshToken(User user);

    AccessTokenWrapper createAccessToken(User user);

    void save(RefreshTokenWrapper refreshTokenWrapper);

    SecurityTokensDto updateTokens(String refreshToken);
}
