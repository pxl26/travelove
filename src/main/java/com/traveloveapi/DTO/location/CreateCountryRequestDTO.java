package com.traveloveapi.DTO.location;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateCountryRequestDTO implements Serializable {
    private String name;
    private String location;
    private String description;
    private String time_zone;
    private String currency;
    private String language;
    private String best_time;
}
