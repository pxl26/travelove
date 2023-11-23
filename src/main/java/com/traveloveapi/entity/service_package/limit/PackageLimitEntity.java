package com.traveloveapi.entity.service_package.limit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "package_limit")
@IdClass(PackageLimitId.class)
public class PackageLimitEntity {
    @Id
    private String service_id;
    @Id
    private int group_1;
    @Id
    private int option_1;
    @Id
    private int group_2;
    @Id
    private int option_2;
    private int limit;
    private int limit_special;
}
