package com.traveloveapi.security.role;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "User";
    }
}
