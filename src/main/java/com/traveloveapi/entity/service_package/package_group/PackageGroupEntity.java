package com.traveloveapi.entity.service_package.package_group;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "package_group")
@IdClass(PackageGroupId.class)
public class PackageGroupEntity {
    @Id
    private String service_id;
    @Id
    private int group_number;
    private String title;
    private int limit_number;
}
