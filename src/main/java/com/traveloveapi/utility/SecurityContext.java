package com.traveloveapi.utility;

import com.traveloveapi.security.role.AdminRole;
import com.traveloveapi.security.role.TourOwnerRole;
import com.traveloveapi.security.role.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityContext {
    static public void setUser(String id, String role) {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        if (role.equals("user"))
            roles.add(new UserRole());
        else if (role.equals("admin")) {
            roles.add(new AdminRole());
        }
        else roles.add(new TourOwnerRole());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, "", roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    static public String getUserID() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    static public boolean isAnonymous(){
        return getUserID().equals("anonymousUser");
    }
}
