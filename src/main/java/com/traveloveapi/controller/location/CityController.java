package com.traveloveapi.controller.location;

import com.traveloveapi.DTO.location.CityDTO;
import com.traveloveapi.DTO.location.CreateCityRequestDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.service.location.CityService;
import com.traveloveapi.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/city")
public class CityController {
    final private CityService cityService;
    final private UserService userService;

    @PostMapping
    @Tag(name = "SPRINT 4: Admin side")
    public CityDTO createCity(@RequestParam String city_name, @RequestParam String country_id, @RequestParam String country_name, @RequestParam String location, @RequestParam String description, @RequestParam String time_zone, @RequestParam Currency currency, @RequestParam String best_time, @RequestParam String dont_miss, @RequestParam MultipartFile thumbnail, @RequestParam MultipartFile[] gallery, @RequestParam String [] gallery_description) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return cityService.create(city_name, country_id, country_name, thumbnail, location, description, time_zone, currency, best_time, dont_miss, gallery, gallery_description);
    }

    @PutMapping
    @Tag(name = "SPRINT 4: Admin side")
    public CityEntity editCity(@RequestParam String city_id, @RequestParam(required = false) String city_name, @RequestParam(required = false) String country_name, @RequestParam(required = false) String country_id, @RequestParam(required = false) String location, @RequestParam(required = false) String description, @RequestParam(required = false) String time_zone, @RequestParam(required = false) Currency currency, @RequestParam(required = false) String best_time, @RequestParam(required = false) String dont_miss, @RequestParam(required = false) MultipartFile cover_pic, @RequestParam(required = false) MultipartFile thumb) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return cityService.edit(city_id, city_name, country_name, country_id, cover_pic, thumb, location, description, time_zone, currency, best_time, dont_miss);
    }
}
