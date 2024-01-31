package com.traveloveapi.service.searching;

import com.traveloveapi.entity.searching.ServiceSearchingEntity;
import com.traveloveapi.repository.searching.ServiceSearchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SearchingService {
    final private ServiceSearchingRepository serviceSearchingRepository;

    public void changeCityName(String old_name, String new_name) {
        ArrayList<ServiceSearchingEntity> list = serviceSearchingRepository.getByCity(old_name);
        for (ServiceSearchingEntity ele:list) {
            ele.setCity_name(new_name);
            serviceSearchingRepository.save(ele);
        }
    }
}
