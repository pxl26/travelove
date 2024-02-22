package com.traveloveapi.controller.collection;

import com.traveloveapi.DTO.collection.CollectionDTO;
import com.traveloveapi.DTO.collection.CreateCollectionDTO;
import com.traveloveapi.service.collection.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {
    final private CollectionService collectionService;

    @PostMapping
    @Tag(name = "SPRINT 4")
    public CollectionDTO create(@RequestBody CreateCollectionDTO request) {
        return collectionService.create(request.getCollection_name(), request.getService_list(), request.getDisplay_on(), request.getRef_id());
    }
}
