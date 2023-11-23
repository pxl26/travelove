package com.traveloveapi.entity.service_package.person_type;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class PackagePersonTypeId implements Serializable {
    private String service_id;
    private int type_number;
}
