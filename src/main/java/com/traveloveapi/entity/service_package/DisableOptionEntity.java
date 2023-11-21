package com.traveloveapi.entity.service_package;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_option")
public class DisableOptionEntity {
    @Id
    private String service_id;
    private String name;
    private int group_number;
    private int option_number;
    private int limit;
    private int limit_special;
    private float price;
    private float price_special;
}
