package com.traveloveapi.DTO.oauth.google;

import lombok.Data;
import java.io.Serializable;

@Data
public class    GoogleTokenResponse implements Serializable {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String scope;
    private String id_token;
}
