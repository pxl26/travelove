package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.searching.SearchingPage;
import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.constrain.OrderType;
import com.traveloveapi.constrain.SortBy;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.ServiceRepository;
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
    final private ServiceRepository serviceRepository;

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
    public SearchingPage searchExtend(@RequestParam int page, @RequestParam int page_size, @RequestParam(required = false) String city_id, @RequestParam(required = false) String country_name, @RequestParam SortBy sort_by, @RequestParam OrderType sort_type, @RequestParam(required = false) String query, @RequestParam(required = false) String currency) {
        if (query!=null) {
            query = SearchingSupporter.sanitize(query);
            ArrayList<ServiceCard> data = tourService.getTourByTitle(query, sort_type, sort_by, page, page_size, currency);
            Long total_record = serviceRepository.getPageTotalTitle(query);
            if (total_record%page_size!=0)
                total_record=total_record/page_size +1;
            else
                total_record=total_record/page_size;
            return new SearchingPage(data, page, page_size, total_record);
        }

        if (city_id!=null)
        {
            ArrayList<ServiceCard> data = tourService.getTourByCity(city_id, sort_type, sort_by, page, page_size, currency);
            Long total_record = serviceRepository.getPageTotalCity(city_id);
            if (total_record%page_size!=0)
                total_record=total_record/page_size +1;
            else
                total_record=total_record/page_size;
            return new SearchingPage(data, page, page_size, total_record);
        }
        else
        {
            ArrayList<ServiceCard> data = tourService.getTourByCountry(country_name, sort_type, sort_by, page, page_size, currency);
            Long total_record = serviceRepository.getPageTotalCountry(country_name);
            if (total_record%page_size!=0)
                total_record=total_record/page_size +1;
            else
                total_record=total_record/page_size;
            return new SearchingPage(data, page, page_size, total_record);
        }
    }
}
