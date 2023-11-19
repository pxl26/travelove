package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "service_detail")
public class ServiceDetailEntity {
    @Id
    private String id;

    private String description;
    private String highlight;
    private String note;
}
