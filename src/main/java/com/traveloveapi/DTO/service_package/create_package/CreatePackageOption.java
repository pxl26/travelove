package com.traveloveapi.DTO.service_package.create_package;

import lombok.Data;

@Data
public class CreatePackageOption {
    private int option_number;
    private String name;
    private float price;
    private float price_special;
    private int limit;
    private int limit_special;
}
