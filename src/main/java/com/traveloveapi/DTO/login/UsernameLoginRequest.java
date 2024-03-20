package com.traveloveapi.DTO.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsernameLoginRequest {
    private String username;
    private String password;
}
