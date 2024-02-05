package com.traveloveapi.controller.location;

import com.traveloveapi.DTO.location.CreateCountryRequestDTO;
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
    public CountryEntity create(@RequestBody CreateCountryRequestDTO req, @RequestParam MultipartFile cover_pic, @RequestParam MultipartFile thumbnail) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return countryService.createCountry(req.getName(), cover_pic, req.getLocation(), thumbnail, req.getDescription(), req.getTime_zone(), req.getCurrency(), req.getBest_time(), req.getLanguage());
    }

    @PutMapping
    @Tag(name = "SPRINT 4: Admin side")
    public CountryEntity edit(@RequestParam String country_id, @RequestParam(required = false) String country_name, @RequestParam(required = false) MultipartFile cover_pic, @RequestParam(required = false) String location, @RequestParam(required = false) MultipartFile thumbnail, @RequestParam(required = false) String description, @RequestParam(required = false) String time_zone, @RequestParam(required = false) Currency currency, @RequestParam(required = false) String best_time, @RequestParam(required = false) Language language) {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return countryService.edit(country_id,country_name,cover_pic,location,thumbnail,description,time_zone,currency,best_time,language);
    }
}
