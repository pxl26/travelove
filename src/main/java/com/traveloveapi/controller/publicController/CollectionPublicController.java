package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.collection.CollectionDTO;
import com.traveloveapi.constrain.CollectionDisplay;
import com.traveloveapi.service.collection.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/collection")
public class CollectionPublicController {
    final private CollectionService collectionService;
    @GetMapping
    @Tag(name = "SPRINT 4: User side")
    public CollectionDTO get(@RequestParam String id) {
        return collectionService.get(id);
    }

    @GetMapping("/home-page")
    @Tag(name = "SPRINT 4: User side")
    public ArrayList<CollectionDTO> getHomepage() {
        return collectionService.getCollectionList(CollectionDisplay.HOME_PAGE, null);
    }

    @GetMapping("/city")
    @Tag(name = "SPRINT 4: User side")
    public ArrayList<CollectionDTO> getByCity(@RequestParam String city_id) {
        return collectionService.getCollectionList(CollectionDisplay.CITY, city_id);
    }

    @GetMapping("/country")
    @Tag(name = "SPRINT 4: User side")
    public ArrayList<CollectionDTO> getByCountry(@RequestParam String country_id) {
        return collectionService.getCollectionList(CollectionDisplay.COUNTRY, country_id);
    }
}
