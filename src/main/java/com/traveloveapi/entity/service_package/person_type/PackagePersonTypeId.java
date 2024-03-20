package com.traveloveapi.entity.service_package.person_type;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PackagePersonTypeId implements Serializable {
    private String service_id;
    private String name;
}
