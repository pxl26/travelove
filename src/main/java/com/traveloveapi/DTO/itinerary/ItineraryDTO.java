package com.traveloveapi.DTO.itinerary;

import lombok.Data;

@Data
public class ItineraryDTO {
    private String tourId;
    private Integer sequence;
    private String time;
    private String content;
    private String media;
}
