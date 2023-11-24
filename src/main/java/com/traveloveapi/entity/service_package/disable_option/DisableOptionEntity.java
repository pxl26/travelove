package com.traveloveapi.entity.service_package.disable_option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "package_disable_option")
@IdClass(DisableOptionEntity.class)
public class DisableOptionEntity implements Serializable {
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
}
