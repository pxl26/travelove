package com.traveloveapi.entity.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "city")
public class CityEntity {
    @Id
    private String id;

    private String name;

    private String country_id;

    private String country;

    private String cover_picture;

    private String location;   //google map url

    private String description;

    private String time_zone;

    private String currency;

    private String greatest_time;
}
