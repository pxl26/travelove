package com.traveloveapi.entity.service_package.package_option;

import jakarta.persistence.*;
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
    @Column(name = "`limit`")
    private Integer limit;
    private Integer limit_special;
}
