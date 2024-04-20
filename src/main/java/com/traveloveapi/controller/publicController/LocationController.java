package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.location.CityDTO;
import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.constrain.OrderType;
import com.traveloveapi.constrain.SortBy;
import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.location.CountryEntity;
import com.traveloveapi.service.location.CityService;
import com.traveloveapi.service.location.CountryService;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class LocationController {
    final private CountryService countryService;
    final private CityService cityService;
    final private TourService tourService;
    @GetMapping("/country")
    @Tag(name = "SPRINT 4")
    public CountryEntity getCountry(@RequestParam(required = false) String id, @RequestParam(required = false) String country_name) {
        return countryService.getCountry(id, country_name);
    }

    @GetMapping("/city")
    @Tag(name = "SPRINT 4")
    public CityDTO getCity(@RequestParam String city_id) {
        return cityService.get(city_id);
    }

    @GetMapping("/city/all-service")
    @Tags({
            @Tag(name = "SPRINT 4")
    })
    public ArrayList<ServiceCard> getAllTour(@RequestParam String city_id) {
        return tourService.getTourByCity(city_id, OrderType.ASCENDED, SortBy.RATING);
    }

    @GetMapping("/country/all-city")
    @Tags({
            @Tag(name = "SPRINT 4"),
            @Tag(name = "SEARCHING")
    })
    public List getAllCity(@RequestParam(required = false) String country_id, @RequestParam(required = false) String country_name) {
        return countryService.getAllCity(country_id, country_name);
    }

    @GetMapping("/country/all-country")
    @Tags({
            @Tag(name = "SPRINT 4"),
            @Tag(name = "SEARCHING")
    })
    public List getAllCountry() {
        return countryService.getAllCountry();
    }

}
