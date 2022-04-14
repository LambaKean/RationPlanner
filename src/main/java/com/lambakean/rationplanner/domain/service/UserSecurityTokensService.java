package com.lambakean.rationplanner.domain.service;

import com.lambakean.rationplanner.data.model.User;
import com.lambakean.rationplanner.data.model.AccessTokenWrapper;
import com.lambakean.rationplanner.data.model.RefreshTokenWrapper;

public interface UserSecurityTokensService {

    RefreshTokenWrapper createRefreshToken(User user);

    AccessTokenWrapper createAccessToken(User user);


}
