package com.traveloveapi.DTO.itinerary;

import lombok.Data;

import java.util.List;

@Data
public class ItineraryRequest {
    private List<ItineraryDTO> list;
}
