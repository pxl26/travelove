package com.traveloveapi.service.location;

import com.traveloveapi.DTO.MediaWithDescription;
import com.traveloveapi.DTO.location.CityDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.SearchingType;
import com.traveloveapi.controller.publicController.MediaController;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.repository.MediaRepository;
import com.traveloveapi.repository.location.CityRepository;
import com.traveloveapi.repository.searching.SearchingRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.searching.SearchingService;
import com.traveloveapi.utility.SearchingSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityService {
    final private CityRepository cityRepository;
    final private S3FileService s3FileService;
    final private SearchingService searchingService;
    final private SearchingRepository searchingRepository;
    final private MediaRepository mediaRepository;

    public CityDTO create(String city_name, String country_id, String country_name, MultipartFile thumbnail, String location, String description, String time_zone, String currency, String best_time, String do_not_miss, @RequestParam MultipartFile[] gallery, @RequestParam String[] gallery_description) {
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

        entity.setCover_pic(s3FileService.uploadFile(gallery[0], "public/city/" + entity.getId() + '/', "cover"));
        entity.setThumbnail(s3FileService.uploadFile(thumbnail, "public/city/" + entity.getId() + '/', "thumbnail"));


        for (int i=0;i<gallery.length;i++)
        {
            MediaEntity media = new MediaEntity();
            media.setId(UUID.randomUUID().toString());
            media.setPath(s3FileService.uploadFile(gallery[i], "public/city/"+entity.getId()+'/',UUID.randomUUID().toString()));
            media.setSeq(i);
            media.setDescription(gallery_description[i]);
            media.setType("CITY-GALLERY");
            media.setRef_id(entity.getId());
            mediaRepository.save(media);
        }

        SearchingEntity searching = new SearchingEntity();
        searching.setRef_id(entity.getId());
        searching.setData(SearchingSupporter.sanitize(city_name));
        searching.setTitle(city_name);
        searching.setType(SearchingType.CITY);
        searching.setCountry_name(country_name);

        searchingRepository.save(searching);
        cityRepository.save(entity);
        return get(entity.getId());
    }

    public CityDTO get(String city_id) {
        CityEntity entity = cityRepository.findById(city_id);
        CityDTO rs = new CityDTO(entity);
        ArrayList<MediaEntity> media_list = mediaRepository.find(city_id, "CITY-GALLERY");
        rs.setGallery(new ArrayList<>());
        for (MediaEntity ele: media_list) {
            MediaWithDescription temp = new MediaWithDescription();
            temp.setSrc(ele.getPath());
            temp.setDescription(ele.getDescription());
            rs.getGallery().add(temp);
        }
        return rs;
    }


    public String getCityName(String city_id) {
        return cityRepository.findById(city_id).getName();
    }

    public CityEntity edit(String id,String city_name, String country_id, String country_name, MultipartFile cover_pic, MultipartFile thumbnail, String location, String description, String time_zone, String currency, String best_time, String do_not_miss) {
        CityEntity entity = cityRepository.findById(id);
        if (city_name!=null) {
            searchingService.changeCityName(entity.getName(), city_name, id);
            entity.setName(city_name);
        }
        if (country_id!=null)
            entity.setCountry_id(country_id);
        if (country_name!=null)
            entity.setCountry_name(country_name);
        if (location!=null)
            entity.setLocation(location);
        if (description!=null)
            entity.setDescription(description);
        if (time_zone!=null)
            entity.setTime_zone(time_zone);
        if (currency!=null)
            entity.setCurrency(currency);
        if (best_time!=null)
            entity.setBest_time(best_time);
        if (do_not_miss!=null)
            entity.setDo_not_miss(do_not_miss);
        if (cover_pic!=null)
            entity.setCover_pic(s3FileService.uploadFile(cover_pic,"public/country/",UUID.randomUUID().toString()));
        if (thumbnail!=null)
            entity.setCover_pic(s3FileService.uploadFile(thumbnail, "public/country/", UUID.randomUUID().toString()));

        cityRepository.update(entity);
        return entity;
    }
}
