package com.lambakean.RationPlanner.security.authentication;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.validityTimeMs}")
    private Long defaultValidityTimeMs;

    public String createToken(@NonNull String userId) {
        return createToken(userId, defaultValidityTimeMs);
    }

    public String createToken(@NonNull String userId, @NonNull Long validityTimeMs) {

        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        Date validUntil = new Date(now.getTime() + validityTimeMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String resolveToken(@NonNull HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String getSubject(@NonNull String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public Long getDefaultValidityTimeMs() {
        return defaultValidityTimeMs;
    }
}
