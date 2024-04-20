package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.searching.SearchingPage;
import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.constrain.OrderType;
import com.traveloveapi.constrain.SortBy;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.repository.searching.SearchingRepository;
import com.traveloveapi.service.tour.TourService;
import com.traveloveapi.utility.SearchingSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/search")
public class SearchingController {
    final private SearchingRepository searchingRepository;
    private final TourService tourService;

    @Tag(name = "SPRINT 4")
    @GetMapping
    public List search(@RequestParam String input) {
        input = SearchingSupporter.sanitize(input);
        return searchingRepository.findByTitle(input, 0, 5);
    }

    @PostMapping
    @Operation(hidden = true)
    public SearchingEntity create(@RequestBody SearchingEntity req) {
        searchingRepository.save(req);
        return req;
    }

    @GetMapping("/extend")
    @Tag(name = "SEARCHING")
    public SearchingPage searchExtend(@RequestParam int page, @RequestParam int page_size, @RequestParam(required = false) String city_name, @RequestParam String country_name) {
        if (city_name!=null)
        {
            ArrayList<ServiceCard> data = tourService.getTourByCity(city_name, OrderType.ASCENDED, SortBy.RATING);
            return new SearchingPage(data, page, page_size, 100);
        }
        return null;
    }
}
