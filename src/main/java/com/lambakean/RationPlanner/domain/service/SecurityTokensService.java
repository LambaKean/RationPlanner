package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.SecurityTokensHolder;
import com.lambakean.RationPlanner.data.model.User;
import com.lambakean.RationPlanner.data.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.data.model.RefreshTokenWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityTokensService {

    RefreshTokenWrapper createRefreshTokenWrapper(User user);

    AccessTokenWrapper createAccessTokenWrapper(User user);

    void save(RefreshTokenWrapper refreshTokenWrapper);

    SecurityTokensHolder updateTokens(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
