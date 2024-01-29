package com.traveloveapi.entity.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "country")
public class CountryEntity {
    @Id
    private String id;

    private String name;

    private String cover_picture;

    private String description;

    private String location;

    private String language;

    private String greatest_time;
}
