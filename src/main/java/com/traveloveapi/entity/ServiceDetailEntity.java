package com.traveloveapi.entity;

import jakarta.persistence.*;
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


    private String currency;

    private String primary_language;

    private String address;

    private String location;
}
