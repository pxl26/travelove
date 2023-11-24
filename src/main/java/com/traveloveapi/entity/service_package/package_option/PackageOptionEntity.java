package com.traveloveapi.entity.service_package.package_option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "package_option")
@IdClass(PackageOptionId.class)
public class PackageOptionEntity {
    @Id
    private String service_id;
    @Id
    private int group_number;
    @Id
    private int option_number;
    private String name;
    private float price;
    private float price_special;
    private int limit;
    private int limit_special;
}
