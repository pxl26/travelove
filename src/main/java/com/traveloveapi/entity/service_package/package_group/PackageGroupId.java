package com.traveloveapi.entity.service_package.package_group;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageGroupId implements Serializable {
    private String service_id;
    private int group_number;
}
