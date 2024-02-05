package com.traveloveapi.DTO.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import lombok.Data;

@Data
public class CreateCountryRequestDTO {
    private String name;
    private String location;
    private String description;
    private String time_zone;
    private Currency currency;
    private Language language;
    private String best_time;
}
