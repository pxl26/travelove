package com.traveloveapi.entity.location;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "country")
public class CountryEntity {
    @Id
    private String id;

    private String country_name;

    private String cover_pic;

    private String thumbnail;

    private String description;

    private String location;

    private String language;

    private String time_zone;

    private String currency;

    private String best_time;
}
