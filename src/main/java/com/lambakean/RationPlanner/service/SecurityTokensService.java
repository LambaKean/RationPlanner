package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.SecurityTokensDto;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.model.RefreshTokenWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityTokensService {

    RefreshTokenWrapper createRefreshTokenWrapper(User user);

    AccessTokenWrapper createAccessTokenWrapper(User user);

    void save(RefreshTokenWrapper refreshTokenWrapper);

    SecurityTokensDto updateTokens(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
