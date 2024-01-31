package com.traveloveapi.controller.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.entity.location.CountryEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.service.location.CountryService;
import com.traveloveapi.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {
    final private CountryService countryService;
    final private UserService userService;
    @PostMapping
    @Tag(name = "SPRINT 4: Admin side")
    public CountryEntity create(@RequestParam String name, @RequestParam MultipartFile cover_pic, @RequestParam String location, @RequestParam MultipartFile thumb, @RequestParam String description, @RequestParam String time_zone, @RequestParam Currency currency, @RequestParam String best_time, @RequestParam Language language) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return countryService.createCountry(name, cover_pic, location, thumb, description, time_zone, currency, best_time, language);
    }
    @GetMapping
    @Tag(name = "SPRINT 4: User side")
    public CountryEntity get(@RequestParam(required = false) String id, @RequestParam(required = false) String country_name) {
        return countryService.getCountry(id, country_name);
    }
}
