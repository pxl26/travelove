package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.TourEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourDetailDTO {
    private String id;
    private String title;
    private String service_owner;
    private float rating;
    private int sold;
    private ServiceStatus status;

    private String description;
    private String highlight;
    private String note;

    TourDetailDTO(ServiceEntity service, TourEntity tour) {
        id = service.getId();
        title = service.getTitle();
        service_owner = service.getService_owner();
        rating = service.getRating();
        sold = service.getSold();
        status = service.getStatus();

        description = tour.getDescription();
        highlight = tour.getHighlight();
        note = tour.getNote();
    }
}
