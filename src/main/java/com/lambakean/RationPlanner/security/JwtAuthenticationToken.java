package com.lambakean.RationPlanner.security;

import com.lambakean.RationPlanner.model.User;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private User user;

    public JwtAuthenticationToken(@NonNull String token,
                                  User user) {
        super(Collections.emptySet());
        this.token = token;
        this.user = user;
    }

    public JwtAuthenticationToken(String token) {
        super(Collections.emptySet());
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public User getPrincipal() {
        return user;
    }
}
