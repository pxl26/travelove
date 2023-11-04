package com.traveloveapi.entity;

import com.traveloveapi.constrain.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name="user")
@Entity
public class UserEntity {
    @Id
    private  String id;
    private String full_name;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
