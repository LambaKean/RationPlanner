package com.lambakean.RationPlanner.security.authentication;

import com.lambakean.RationPlanner.model.BaseEntity;
import com.lambakean.RationPlanner.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenWrapper extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresIn;

    public RefreshTokenWrapper(String token, User user, LocalDateTime expiresIn) {
        this.token = token;
        this.user = user;
        this.expiresIn = expiresIn;
    }

    public RefreshTokenWrapper() {}

    public boolean isTokenValid() {
        return this.expiresIn.isAfter(LocalDateTime.now());
    }

    public boolean tokenBelongsTo(User candidate) {
        return Objects.equals(user.getId(), candidate.getId());
    }


    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}