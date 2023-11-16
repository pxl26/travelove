package com.traveloveapi.entity;

import com.traveloveapi.constrain.Gender;
import com.traveloveapi.constrain.Region;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="user_detail")
public class UserDetailEntity {
    @Id
    private String user_id;
    private String username;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date birth;
    private String password;
    private Timestamp create_at;
    @Enumerated(EnumType.STRING)
    private Region region;
}
