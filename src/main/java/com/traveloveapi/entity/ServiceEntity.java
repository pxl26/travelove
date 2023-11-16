package com.traveloveapi.entity;

import com.traveloveapi.constrain.ServiceStatus;
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
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
}
