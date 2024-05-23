package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourDTO {
    private ServiceType service_type;
    private String title;
    private String highlight;
    private String note;
    private String description;
    private String currency;
    private String pimary_language;
    private String city_id;
}
