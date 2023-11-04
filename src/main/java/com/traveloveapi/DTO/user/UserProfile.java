package com.traveloveapi.DTO.user;

import com.traveloveapi.constrain.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
    private String id;
    private String full_name;
    private String avatar;
    private Roles role;
    private String region;
    private String birth;
    private String username;
    private String gender;
    private Timestamp create_at;
}
