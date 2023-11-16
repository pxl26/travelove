package com.traveloveapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Data
@Table(name = "tour")
public class TourEntity {
    @Id
    private String id;

    private String description;
    private String highlight;
    private String note;
}
