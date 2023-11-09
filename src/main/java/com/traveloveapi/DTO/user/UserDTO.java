package com.traveloveapi.DTO.user;

import com.traveloveapi.constrain.Region;
import com.traveloveapi.constrain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String full_name;
    private String avatar;
    private Role role;
    private String username;
    private Region region;

    public UserDTO(UserProfile profile) {
        id = profile.getId();
        full_name = profile.getFull_name();
        avatar = profile.getAvatar();
        role = profile.getRole();
        username = profile.getUsername();
        region = profile.getRegion();
    }
}
