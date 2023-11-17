package com.traveloveapi.entity;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.constrain.ServiceType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="service")
public class ServiceEntity {
    @Id
    private String id;
    private String title;
    private String service_owner;
    private float rating;
    private int sold;
    private String thumbnail;
    @Enumerated(EnumType.STRING)
    private ServiceType type;
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
}
