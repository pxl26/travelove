package com.traveloveapi.utility;

import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

public class JwtProvider {
    static private final String JWT_SECRET = "PhanLocccc";
    public static String generateToken(String user_id, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return (String)Jwts.builder().setSubject(user_id).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    static public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
