package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.collection.CollectionDTO;
import com.traveloveapi.service.collection.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/collection")
public class CollectionPublicController {
    final private CollectionService collectionService;
    @GetMapping
    @Tag(name = "SPRINT 4: User side")
    public CollectionDTO get(String id) {
        return collectionService.get(id);
    }
}
