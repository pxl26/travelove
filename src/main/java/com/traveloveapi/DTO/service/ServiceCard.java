package com.traveloveapi.DTO.service;

import lombok.Data;

@Data
public class ServiceCard {
    private String service_id;
    private String title;
    private float rating;
    private int vote_quantity;
    private int sold;
    private float min_price;
    private String city;
    private String country;
    private String thumbnail;
}
