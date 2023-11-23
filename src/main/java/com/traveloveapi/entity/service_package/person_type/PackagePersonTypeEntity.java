package com.traveloveapi.entity.service_package.person_type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_person_type")
@IdClass(PackagePersonTypeId.class)
public class PackagePersonTypeEntity {
    @Id
    private String service_id;
    @Id
    private int type_number;
    private String name;
    private float bonus_price;
}
