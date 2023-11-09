package com.traveloveapi.DTO.user;

import com.traveloveapi.constrain.Gender;
import com.traveloveapi.constrain.Region;
import com.traveloveapi.constrain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
    private String id;
    private String full_name;
    private String avatar;
    private Role role;
    private Region region;
    private Date birth;
    private String username;
    private Gender gender;
    private Timestamp create_at;
}
