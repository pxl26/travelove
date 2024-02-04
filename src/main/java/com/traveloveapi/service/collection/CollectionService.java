package com.traveloveapi.service.collection;

import com.traveloveapi.DTO.collection.CollectionDTO;
import com.traveloveapi.entity.collection.CollectionDetailEntity;
import com.traveloveapi.entity.collection.CollectionEntity;
import com.traveloveapi.repository.collection.CollectionDetailRepository;
import com.traveloveapi.repository.collection.CollectionRepository;
import com.traveloveapi.service.tour.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionService {
    final private CollectionRepository collectionRepository;
    final private CollectionDetailRepository collectionDetailRepository;
    final private TourService tourService;

    public CollectionDTO create(String name, String[] service_list) {
        CollectionEntity entity = new CollectionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(name);
        collectionRepository.save(entity);
        int k=0;
        for (String ele: service_list) {
            CollectionDetailEntity detail = new CollectionDetailEntity();
            detail.setCollection_id(entity.getId());
            detail.setService_id(ele);
            detail.setSeq(k++);
            collectionDetailRepository.save(detail);
        }

        return get(entity.getId());
    }

    public CollectionDTO get(String id) {
        CollectionDTO rs = new CollectionDTO();
        CollectionEntity entity = collectionRepository.find(id);
        rs.setId(id);
        rs.setName(entity.getName());
        ArrayList<CollectionDetailEntity> list = collectionDetailRepository.find(id);
        rs.setService_list(new ArrayList<>());
        for (CollectionDetailEntity ele: list)
            rs.getService_list().add(tourService.createCard(ele.getService_id()));

        return rs;
    }
}
