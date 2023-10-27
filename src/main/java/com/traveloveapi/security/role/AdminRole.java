package com.traveloveapi.security.role;

import org.springframework.security.core.GrantedAuthority;

public class AdminRole implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
