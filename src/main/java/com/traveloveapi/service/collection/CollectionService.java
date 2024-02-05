package com.traveloveapi.service.collection;

import com.traveloveapi.DTO.collection.CollectionDTO;
import com.traveloveapi.constrain.CollectionDisplay;
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

    public CollectionDTO create(String name, String[] service_list, CollectionDisplay display_on, String ref_id) {
        CollectionEntity entity = new CollectionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(name);
        entity.setDisplay_on(display_on);
        entity.setRef_id(ref_id);
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
    public ArrayList<CollectionDTO> getCollectionList(CollectionDisplay display_at, String ref_id) {
        ArrayList<CollectionEntity> data = collectionRepository.getList(display_at, ref_id);
        if (data.isEmpty())
            return new ArrayList<>();
        ArrayList<CollectionDTO> rs = new ArrayList<>();
        for (CollectionEntity ele: data)
            rs.add(get(ele.getId()));
        return rs;
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
