package com.traveloveapi.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTripSummary {
    private String user_id;
    private Double total_tour;
    private Double total_trip;
    private Double total_country;
    private Double total_city;
}
