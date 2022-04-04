package com.lambakean.RationPlanner.security.authentication;

import com.lambakean.RationPlanner.model.User;

import java.time.ZonedDateTime;

/**
 * Обёртка для access токенов
 */
public class AccessTokenWrapper {

    private String token;

    private User user;
    private ZonedDateTime expiresAt;

    public AccessTokenWrapper(String token, User user, ZonedDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public AccessTokenWrapper() {}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
