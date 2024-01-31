package com.traveloveapi.entity.location;

import com.traveloveapi.constrain.Currency;
import jakarta.persistence.*;
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

    @Column(name = "dont_miss")
    private String do_not_miss;

    private String location;   //google map url

    private String description;

    private String time_zone;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String best_time;
}
