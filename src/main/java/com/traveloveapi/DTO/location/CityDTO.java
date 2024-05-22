package com.traveloveapi.DTO.location;

import com.traveloveapi.DTO.MediaWithDescription;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.entity.location.CityEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CityDTO {
    private String city_id;
    private String city_name;
    private String country;
    private String cover_pic;
    private String thumbnail;
    private String location;
    private String description;
    private String time_zone;
    private String currency;
    private String best_time;
    private String dont_miss;
    private ArrayList<MediaWithDescription> gallery;

    public CityDTO(CityEntity entity) {
        city_id = entity.getId();
        city_name = entity.getName();
        country = entity.getCountry_name();
        cover_pic = entity.getCover_pic();
        thumbnail = entity.getThumbnail();
        location = entity.getLocation();
        description = entity.getDescription();
        time_zone = entity.getTime_zone();
        currency = entity.getCurrency();
        best_time = entity.getBest_time();
        dont_miss = entity.getDo_not_miss();
    }
}
