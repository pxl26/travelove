package com.traveloveapi.entity.service_package.limit;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class PackageLimitId implements Serializable {
    private String service_id;
    private int group_1;
    private int option_1;
    private int group_2;
    private int option_2;
}
