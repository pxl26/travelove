package com.traveloveapi.entity.collection;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CollectionDetailID implements Serializable {
    private String collection_id;
    private String service_id;
}
