package com.lambakean.RationPlanner.model;

import java.time.ZonedDateTime;

public class SecurityTokensHolder {

    private User user;

    private AccessTokenWrapper accessTokenWrapper;
    private RefreshTokenWrapper refreshTokenWrapper;

    public SecurityTokensHolder() {}

    public SecurityTokensHolder(User user,
                                AccessTokenWrapper accessTokenWrapper,
                                RefreshTokenWrapper refreshTokenWrapper) {
        this.user = user;
        this.accessTokenWrapper = accessTokenWrapper;
        this.refreshTokenWrapper = refreshTokenWrapper;
    }

    public String getUserId() {

        if(user == null) return null;
        return user.getId();
    }

    public String getAccessToken() {

        if(accessTokenWrapper == null) return null;
        return accessTokenWrapper.getToken();
    }

    public ZonedDateTime getAccessTokenExpiresAt() {

        if(accessTokenWrapper == null) return null;
        return accessTokenWrapper.getExpiresAt();
    }

    public AccessTokenWrapper getAccessTokenWrapper() {
        return accessTokenWrapper;
    }

    public void setAccessTokenWrapper(AccessTokenWrapper accessTokenWrapper) {
        this.accessTokenWrapper = accessTokenWrapper;
    }

    public RefreshTokenWrapper getRefreshTokenWrapper() {
        return refreshTokenWrapper;
    }

    public void setRefreshTokenWrapper(RefreshTokenWrapper refreshTokenWrapper) {
        this.refreshTokenWrapper = refreshTokenWrapper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
