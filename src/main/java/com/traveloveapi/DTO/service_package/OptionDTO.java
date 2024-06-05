package com.traveloveapi.DTO.service_package;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionDTO {
    private int group_number;
    private int option_number;
    private String name;
    private Double price;
    private Double price_special;
}
