package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "otp")
public class OtpEntity {
    @Id
    private String id;
    private String user_id;
    private String type;
    private String code;
    private Timestamp expiration;
    private String address;
    private String note;
}
