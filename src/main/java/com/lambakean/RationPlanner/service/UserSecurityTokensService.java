package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.model.AccessTokenWrapper;
import com.lambakean.RationPlanner.model.RefreshTokenWrapper;

public interface UserSecurityTokensService {

    RefreshTokenWrapper createRefreshToken(User user);

    AccessTokenWrapper createAccessToken(User user);


}
