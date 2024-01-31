package com.traveloveapi.controller.location;

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
    public CityEntity createCity(@RequestParam String city_name, @RequestParam String country_name, @RequestParam String country_id, @RequestParam String location, @RequestParam String description, @RequestParam String time_zone, @RequestParam Currency currency, @RequestParam String best_time, @RequestParam String dont_miss, @RequestParam MultipartFile cover_pic, @RequestParam MultipartFile thumb) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return cityService.create(city_name, country_id, country_name, cover_pic, thumb, location, description, time_zone, currency, best_time, dont_miss);
    }
}
