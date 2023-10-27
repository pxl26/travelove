package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="user_detail")
public class UserDetailEntity {
    @Id
    private String user_id;
    private String email;
    private String phone;
    private String gender;
    private String birth;
    private String password;
    private Timestamp create_at;
}
