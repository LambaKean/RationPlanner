package com.lambakean.RationPlanner.security.authentication;

import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public JwtAuthenticationProvider(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(@NonNull Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString();

        if(jwtTokenProvider.validateToken(token)) {

            String userId = jwtTokenProvider.getSubject(token);

            User user = userService.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("The user with id [%s] was not found", userId))
            );

            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token, user);
            authenticationToken.setAuthenticated(true);

            return authenticationToken;

        } else {
            throw new BadCredentialsException(String.format("The token [%s] is invalid", token));
        }

    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthenticationToken.class.equals(authenticationClass);
    }
}
