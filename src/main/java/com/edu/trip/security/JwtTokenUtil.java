package com.edu.trip.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    public static final String CLAIM_KEY_USERNAME = "username";
    public static final String CLAIM_KEY_ROLE = "userRole";

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    @Value(value = "${jwt.secret}")
    private String SECRET;

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_ROLE, role);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    public Claims parseTokenClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
