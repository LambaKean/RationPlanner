package com.lambakean.RationPlanner.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        if(accessToken != null) {
            try {
                Authentication authentication = jwtAuthenticationProvider.authenticate(
                    new JwtAuthenticationToken(accessToken)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException ignored) {}
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
