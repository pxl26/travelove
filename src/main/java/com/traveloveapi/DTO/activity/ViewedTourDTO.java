package com.traveloveapi.DTO.activity;

import com.traveloveapi.constrain.UserAction;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class ViewedTourDTO {
    private String tour_id;

    private String title;

    private String thumb;

    private float min_price;
}
