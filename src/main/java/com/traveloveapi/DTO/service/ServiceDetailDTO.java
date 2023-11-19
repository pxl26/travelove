package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.ServiceDetailEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class ServiceDetailDTO {
    private String id;
    private String title;
    private String service_owner;
    private float rating;
    private int sold;
    private ServiceStatus status;
    private String thumbnail;

    private String description;
    private String highlight;
    private String note;
    private ArrayList<MediaEntity> gallery;

    public ServiceDetailDTO(ServiceEntity service, ServiceDetailEntity tour, ArrayList<MediaEntity> media) {
        id = service.getId();
        title = service.getTitle();
        service_owner = service.getService_owner();
        rating = service.getRating();
        sold = service.getSold();
        status = service.getStatus();

        description = tour.getDescription();
        highlight = tour.getHighlight();
        note = tour.getNote();
        thumbnail = service.getThumbnail();
        this.gallery = media;
    }

}
