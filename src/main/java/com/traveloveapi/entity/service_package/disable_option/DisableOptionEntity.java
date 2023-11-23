package com.traveloveapi.entity.service_package.disable_option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "package_option")
@IdClass(DisableOptionEntity.class)
public class DisableOptionEntity implements Serializable {
    @Id
    private String service_id;
    @Id
    private String name;
    @Id
    private int group_number;
    @Id
    private int option_number;
    @Id
    private int limit;
    @Id
    private int limit_special;
    @Id
    private float price;
    @Id
    private float price_special;
}
