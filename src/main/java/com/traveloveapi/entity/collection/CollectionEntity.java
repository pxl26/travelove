package com.traveloveapi.entity.collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "collection")
public class CollectionEntity {
    @Id
    private String id;
    private String name;
}
