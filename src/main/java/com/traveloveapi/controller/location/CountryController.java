package com.traveloveapi.controller.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.entity.location.CountryEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.service.location.CountryService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {
    final private CountryService countryService;
    final private UserService userService;
    @PostMapping
    public CountryEntity create(@RequestParam String name, @RequestParam MultipartFile cover_pic, @RequestParam String location, @RequestParam MultipartFile thumb, @RequestParam String description, @RequestParam String time_zone, @RequestParam Currency currency, @RequestParam String best_time, @RequestParam Language language) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return countryService.createCountry(name, cover_pic, location, thumb, description, time_zone, currency, best_time, language);
    }
}
