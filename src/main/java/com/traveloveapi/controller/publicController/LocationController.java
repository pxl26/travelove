package com.traveloveapi.controller.publicController;

import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.location.CountryEntity;
import com.traveloveapi.service.location.CityService;
import com.traveloveapi.service.location.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class LocationController {
    final private CountryService countryService;
    final private CityService cityService;
    @GetMapping("/country")
    @Tag(name = "SPRINT 4: User side")
    public CountryEntity getCountry(@RequestParam(required = false) String id, @RequestParam(required = false) String country_name) {
        return countryService.getCountry(id, country_name);
    }

    @GetMapping("/city")
    @Tag(name = "SPRINT 4: User side")
    public CityEntity getCity(@RequestParam String city_id) {
        return cityService.get(city_id);
    }

    @GetMapping("/country/all-city")
    @Tags({
            @Tag(name = "SPRINT 4: User side"),
            @Tag(name = "SPRINT 4: Admin side"),
            @Tag(name = "SPRINT 4: Service owner side")})
    public List getAllCity(@RequestParam(required = false) String country_id, @RequestParam(required = false) String country_name) {
        return countryService.getAllCity(country_id, country_name);
    }

    @GetMapping("/country/all-country")
    @Tags({
            @Tag(name = "SPRINT 4: User side"),
            @Tag(name = "SPRINT 4: Admin side"),
            @Tag(name = "SPRINT 4: Service owner side")})
    public List getAllCountry() {
        return countryService.getAllCountry();
    }
}