package com.traveloveapi.entity.service_package.option_special;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "package_option_special")
@IdClass(OptionSpecialId.class)
@AllArgsConstructor
@NoArgsConstructor
public class OptionSpecialEntity {
    @Id
    private String service_id;
    @Id
    private int group_number;
    @Id
    private int option_number;
    private boolean is_disable;
}
