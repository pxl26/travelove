package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.service.currency.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailDTO implements Serializable {
    private String id;
    private String title;
    private String service_owner;
    private float rating;
    private int sold;
    private ServiceStatus status;
    private String thumbnail;

    private String description;
    private String highlight;
    private String address;
    private String location;
    private String note;
    private Double min_price;
    private String currency;
    private String primary_language;
    private ArrayList<MediaEntity> gallery;
    private String city_id;
    private String originCurrency;

    private boolean wish;

    public ServiceDetailDTO(ServiceEntity service, ServiceDetailEntity tour, ArrayList<MediaEntity> media, boolean isWish, String currency, Double converted_min_price) {
        id = service.getId();
        title = service.getTitle();
        service_owner = service.getService_owner();
        rating = service.getRating();
        sold = service.getSold();
        status = service.getStatus();

        description = tour.getDescription();
        highlight = tour.getHighlight();
        address = tour.getAddress();
        location = tour.getLocation();
        note = tour.getNote();
        thumbnail = service.getThumbnail();
        originCurrency = tour.getCurrency();
        primary_language = tour.getPrimary_language();
        gallery = media;
        city_id = service.getCity_id();

        wish = isWish;

        min_price = currency.equals(originCurrency) ? service.getMin_price() : converted_min_price;
    }

}
