package com.traveloveapi.service.location;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.repository.location.CityRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityService {
    final private CityRepository cityRepository;
    final private S3FileService s3FileService;

    public CityEntity create(String city_name, String country_id, String country_name, MultipartFile cover_pic, MultipartFile thumbnail, String location, String description, String time_zone, Currency currency, String best_time, String do_not_miss) {
        CityEntity entity = new CityEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(city_name);
        entity.setCountry_id(country_id);
        entity.setCountry_name(country_name);
        entity.setLocation(location);
        entity.setDescription(description);
        entity.setTime_zone(time_zone);
        entity.setCurrency(currency);
        entity.setBest_time(best_time);
        entity.setDo_not_miss(do_not_miss);

        entity.setCover_pic(s3FileService.uploadFile(cover_pic, "public/city/" + country_name + '/' + city_name + '/', "cover"));
        entity.setThumbnail(s3FileService.uploadFile(thumbnail, "public/city/" + country_name + '/' + city_name + '/', "thumbnail"));
        cityRepository.save(entity);
        return entity;
    }

    public CityEntity get(String city_id) {
        return cityRepository.findById(city_id);
    }
}
