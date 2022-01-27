package com.lambakean.RationPlanner.security.authentication;

import com.lambakean.RationPlanner.model.User;

import java.time.LocalDateTime;

public class AccessTokenWrapper {

    private String token;

    private User user;
    private LocalDateTime expiresIn;

    public AccessTokenWrapper(String token, User user, LocalDateTime expiresIn) {
        this.token = token;
        this.user = user;
        this.expiresIn = expiresIn;
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

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}
