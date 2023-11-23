package com.traveloveapi.entity.service_package.package_option;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class PackageOptionId implements Serializable {
    private String service_id;
    private int group_number;
    private int option_number;
}
