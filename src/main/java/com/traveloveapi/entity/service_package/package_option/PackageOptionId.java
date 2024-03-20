package com.traveloveapi.entity.service_package.package_option;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PackageOptionId implements Serializable {
    private String service_id;
    private int group_number;
    private int option_number;
}
