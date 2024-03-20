package com.traveloveapi.DTO.user;

import com.traveloveapi.constrain.Gender;
import com.traveloveapi.constrain.Region;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
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
    private String region;
    private Date birth;
    private String username;
    private Gender gender;
    private Timestamp create_at;

    public UserProfile(UserEntity user, UserDetailEntity detail) {
        id = user.getId();
        full_name = user.getFull_name();
        avatar = user.getAvatar();
        role = user.getRole();
        region = detail.getRegion();
        birth = detail.getBirth();
        username = detail.getUsername();
        gender = detail.getGender();
        create_at = detail.getCreate_at();
    }
}
