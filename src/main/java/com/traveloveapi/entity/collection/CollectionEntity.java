package com.traveloveapi.entity.collection;

import com.traveloveapi.constrain.CollectionDisplay;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "collection")
public class CollectionEntity {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CollectionDisplay display_on;

    private String ref_id;
}
