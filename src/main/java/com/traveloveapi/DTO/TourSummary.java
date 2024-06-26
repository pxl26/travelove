package com.traveloveapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourSummary {
    private String tour_id;
    private String tour_title;
    private Float rating;
    private Integer feedbackQuantity;
    private Integer sold;
    private Double income;
    private String thumbnail;
}
