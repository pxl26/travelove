package com.traveloveapi.entity.collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "collection_detail")
@IdClass(CollectionDetailID.class)
public class CollectionDetailEntity {
    @Id
    private String collection_id;

    @Id
    private String service_id;

    private int seq;
}
