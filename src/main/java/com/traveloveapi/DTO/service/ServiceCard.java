package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceStatus;
import lombok.Data;

@Data
public class ServiceCard {
    private String service_id;
    private String title;
    private float rating;
    private int vote_quantity;
    private int sold;
    private Double min_price;
    private String currency;
    private String city;
    private String country;
    private String thumbnail;
    private ServiceStatus status;
}
