package com.traveloveapi.entity.service_package.limit;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "package_limit")
@IdClass(PackageLimitId.class)
@Data
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
    @Column(name = "`limit`")
    private Integer limit;
    private Integer limit_special;
}
