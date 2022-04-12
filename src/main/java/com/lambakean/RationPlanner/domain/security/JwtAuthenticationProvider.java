package com.lambakean.RationPlanner.domain.security;

import com.lambakean.RationPlanner.domain.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.data.model.User;
import com.lambakean.RationPlanner.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();

        if(jwtTokenProvider.validateToken(token)) {

            String userId = jwtTokenProvider.getSubject(token);

            User user;

            try {
                user = userService.findById(userId);
            } catch (EntityNotFoundException e) {
                throw new BadCredentialsException(e.getMessage());
            }

            UserAuthentication userAuthentication = new UserAuthentication(token, user);
            userAuthentication.setAuthenticated(true);

            return userAuthentication;

        } else {
            throw new BadCredentialsException(String.format("The access token [%s] is invalid", token));
        }

    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return UserAuthentication.class.equals(authenticationClass);
    }
}
