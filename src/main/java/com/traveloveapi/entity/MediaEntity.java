package com.traveloveapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "media")
public class MediaEntity {
    @Id
    private String id;

    private String ref_id;

    private String path;

    private String type;

    private String description;

    private int seq;
}
