package com.traveloveapi.entity.service_package.package_option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_option")
@IdClass(PackageOptionId.class)
public class PackageOptionEntity {
    @Id
    private String service_id;
    @Id
    private String group_number;
    @Id
    private String option_number;
    private String name;
    private String price;
    private String price_special;
    private String limit;
    private String limit_special;
}
