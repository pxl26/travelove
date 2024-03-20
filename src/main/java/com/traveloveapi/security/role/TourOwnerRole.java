package com.traveloveapi.security.role;

import org.springframework.security.core.GrantedAuthority;

public class TourOwnerRole implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "Tour_Owner";
    }
}
