package com.traveloveapi.service.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.entity.location.CountryEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.location.CityRepository;
import com.traveloveapi.repository.location.CountryRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.utility.FileSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryService {
    final private CountryRepository countryRepository;
    final private S3FileService s3FileService;
    final private CityRepository cityRepository;

    public CountryEntity createCountry(String name, MultipartFile cover_pic, String location, MultipartFile thumb, String description, String time_zone, Currency currency, String best_time, Language language) {
        CountryEntity entity = new CountryEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setCountry_name(name);
        entity.setLocation(location);
        entity.setDescription(description);
        entity.setTime_zone(time_zone);
        entity.setCurrency(currency);
        entity.setBest_time(best_time);
        entity.setLanguage(language);

        entity.setCover_pic(s3FileService.uploadFile(cover_pic,"public/country/"+ name + '/',"cover"));
        entity.setThumbnail(s3FileService.uploadFile(thumb,"public/country/"+ name + '/',"thumbnail"));

        countryRepository.save(entity);
        return entity;
    }

    public CountryEntity getCountry(String id, String name) {
        if (id!=null)
            return countryRepository.findById(id);
        if (name!=null)
            return countryRepository.findByName(name);
        throw new CustomException("Require 1 param at least", 400);
    }

    public List getAllCity(String id, String name) {
        if (id!=null)
            return cityRepository.getAllCityByICountryId(id);
        if (name!=null)
            return cityRepository.getAllCityByICountryName(name);
        throw new CustomException("Require 1 param at least", 400);
    }
}
