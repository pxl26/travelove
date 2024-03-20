package com.traveloveapi.DTO.location;

import com.traveloveapi.constrain.Currency;
import lombok.Data;

@Data
public class CreateCityRequestDTO {
    private String city_name;
    private String country_name;
    private String country_id;
    private String location;
    private String description;
    private String time_zone;
    private Currency currency;
    private String best_time;
    private String dont_miss;
}
