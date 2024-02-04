package com.traveloveapi.DTO.collection;

import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.collection.CollectionEntity;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CollectionDTO {
    private String id;
    private String name;
    private ArrayList<ServiceCard> service_list;
}
