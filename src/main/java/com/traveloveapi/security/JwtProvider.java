package com.traveloveapi.security;

import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private final String JWT_SECRET = "PhanLocccc";
    public String generateToken(String user_id, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return (String)Jwts.builder().setSubject(user_id).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();

    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
