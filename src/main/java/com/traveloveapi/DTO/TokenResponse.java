package com.traveloveapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private Timestamp create_at;
    private String expiration;
}
