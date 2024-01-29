package com.traveloveapi.entity;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
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

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Language primary_language;

    private String address;

    private String location;
}
