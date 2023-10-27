package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String role;
}
