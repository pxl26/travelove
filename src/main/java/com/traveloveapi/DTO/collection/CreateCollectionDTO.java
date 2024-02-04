package com.traveloveapi.DTO.collection;

import lombok.Data;

@Data
public class CreateCollectionDTO {
    private String collection_name;
    private String[] service_list;
}
