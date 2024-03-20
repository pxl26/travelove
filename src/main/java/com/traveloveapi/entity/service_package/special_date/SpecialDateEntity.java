package com.traveloveapi.entity.service_package.special_date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name = "package_special_date")
@IdClass(SpecialDateEntity.class)
@AllArgsConstructor
@NoArgsConstructor
public class SpecialDateEntity implements Serializable {
    @Id
    private String service_id;
    @Id
    private String type;
    @Id
    private int seq;
}
