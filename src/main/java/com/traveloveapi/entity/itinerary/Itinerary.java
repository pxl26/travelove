package com.traveloveapi.entity.itinerary;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Itinerary {
    @Id
    private String id;

    private String tourId;

    private String time;

    private String content;

    private String media;

    private Integer sequence;
}