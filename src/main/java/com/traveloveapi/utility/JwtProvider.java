package com.traveloveapi.utility;

import java.sql.Timestamp;
import java.util.Date;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.constrain.Role;
import io.jsonwebtoken.*;

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

    static public TokenResponse generateTokenResponse(String id, Role role) {
        TokenResponse response = new TokenResponse();
        long access=0L;
        long refresh=0L;
        String desc = "";
        switch (role) {
            case USER:
                access = 86400000L;    //24h
                refresh = 172800000L;  //48h
                desc = "24h-48h";

//                access = 15000L;
//                refresh = 30000L;
//                desc = "15s-30s";
                break;
            case ADMIN:
                access = 900000L;   //15'
                refresh = 2700000L; //45'
                desc = "15m-45m";
                break;
            case TOUR_OWNER:
                access = 900000L;   //15'
                refresh = 5400000L; //95'
                desc = "15m-95m";
                break;
        }
        response.setAccess_token(JwtProvider.generateToken(id, access));
        response.setRefresh_token(JwtProvider.generateToken(id, refresh));
        response.setExpiration(desc);
        response.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return response;
    }
}
