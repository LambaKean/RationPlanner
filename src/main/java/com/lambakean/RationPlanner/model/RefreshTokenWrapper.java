package com.lambakean.RationPlanner.model;

import com.lambakean.RationPlanner.model.BaseEntity;
import com.lambakean.RationPlanner.model.User;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Обертка для refresh токенов. Облегчает работу с refresh токенами в коде и позволяет хранить эти токены в базе данных
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenWrapper extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private ZonedDateTime expiresAt;

    public RefreshTokenWrapper(String token, User user, ZonedDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public RefreshTokenWrapper() {}

    public boolean isExpired() {
        return this.expiresAt.isBefore(ZonedDateTime.now());
    }

    public boolean tokenBelongsTo(User candidate) {
        return Objects.equals(user.getId(), candidate.getId());
    }


    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresIn) {
        this.expiresAt = expiresIn;
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