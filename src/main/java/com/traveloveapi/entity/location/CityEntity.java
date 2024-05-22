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

    private String country_name;

    private String cover_pic;

    private String thumbnail;

    @Column(name = "dont_miss")
    private String do_not_miss;

    private String location;   //google map url

    private String description;

    private String time_zone;

    private String currency;

    private String best_time;
}
