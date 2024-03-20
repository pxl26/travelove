package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="google")
public class GoogleEntity {
    @Id
    private String id;

    private String user_id;

    private String given_name;

    private String email;

    private boolean verified_email;

    private String name;

    private String family_name;

    private String picture;

    private String locale;

    private String access_token;

    private String refresh_token;

    private String id_token;

    private String scope;

    private String expires_in;
}