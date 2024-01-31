package com.traveloveapi.entity.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
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

    @Enumerated(EnumType.STRING)
    private Language language;

    private String time_zone;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String best_time;
}
