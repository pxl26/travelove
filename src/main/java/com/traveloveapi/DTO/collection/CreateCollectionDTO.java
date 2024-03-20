package com.traveloveapi.DTO.collection;

import com.traveloveapi.constrain.CollectionDisplay;
import lombok.Data;

@Data
public class CreateCollectionDTO {
    private String collection_name;
    private String[] service_list;
    private CollectionDisplay display_on;
    private String ref_id;
}
