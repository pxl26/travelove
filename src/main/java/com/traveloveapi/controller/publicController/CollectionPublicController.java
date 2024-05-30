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
    @Tag(name = "SPRINT 4")
    public CollectionDTO get(@RequestParam String id, @RequestParam(required = false) String currency) {
        return collectionService.get(id, currency);
    }

    @GetMapping("/home-page")
    @Tag(name = "SPRINT 4")
    public ArrayList<CollectionDTO> getHomepage(@RequestParam(required = false) String currency) {
        return collectionService.getCollectionList(CollectionDisplay.HOME_PAGE, null, currency);
    }

    @GetMapping("/city")
    @Tag(name = "SPRINT 4")
    public ArrayList<CollectionDTO> getByCity(@RequestParam String city_id, @RequestParam(required = false) String currency) {
        return collectionService.getCollectionList(CollectionDisplay.CITY, city_id, currency);
    }

    @GetMapping("/country")
    @Tag(name = "SPRINT 4")
    public ArrayList<CollectionDTO> getByCountry(@RequestParam String country_id, @RequestParam(required = false) String currency) {
        return collectionService.getCollectionList(CollectionDisplay.COUNTRY, country_id, currency);
    }
}
