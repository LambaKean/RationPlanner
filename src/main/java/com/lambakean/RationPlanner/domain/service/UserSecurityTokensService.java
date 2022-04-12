package com.lambakean.RationPlanner.domain.service;

import com.lambakean.RationPlanner.data.model.User;
import com.lambakean.RationPlanner.data.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.data.model.RefreshTokenWrapper;

public interface UserSecurityTokensService {

    RefreshTokenWrapper createRefreshToken(User user);

    AccessTokenWrapper createAccessToken(User user);


}
