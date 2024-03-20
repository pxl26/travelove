package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "wish_list")
public class WishListEntity {
    @Id
    private String id;

    private String user_id;

    private String service_id;

    private Timestamp create_at;
}
