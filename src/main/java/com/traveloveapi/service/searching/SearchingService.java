package com.traveloveapi.service.searching;

import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.repository.searching.SearchingRepository;
import com.traveloveapi.utility.SearchingSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SearchingService {
    final private SearchingRepository searchingRepository;

    public void changeCityName(String old_name, String new_name,String id) {
        ArrayList<SearchingEntity> list = searchingRepository.getByCity(old_name);
        for (SearchingEntity ele:list) {
            ele.setData(SearchingSupporter.sanitize(new_name));
            ele.setCity_name(new_name);
            searchingRepository.save(ele);
        }
        SearchingEntity entity = searchingRepository.find(id);
        entity.setTitle(new_name);
        searchingRepository.save(entity);
    }

    public void changeCountryName(String old_name, String new_name, String id) {
        ArrayList<SearchingEntity> list = searchingRepository.getByCountry(old_name);
        for (SearchingEntity ele:list) {
            ele.setData(SearchingSupporter.sanitize(new_name));
            ele.setCountry_name(new_name);
            searchingRepository.save(ele);
        }
        SearchingEntity entity = searchingRepository.find(id);
        entity.setTitle(new_name);
        searchingRepository.save(entity);
    }
}
