package com.traveloveapi.service.itinerary;

import com.traveloveapi.DTO.itinerary.ItineraryDTO;
import com.traveloveapi.entity.itinerary.Itinerary;
import com.traveloveapi.repository.itinerary.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryService {
    private final ItineraryRepository itineraryRepository;

    public List<Itinerary> createNewItinerary(List<ItineraryDTO> itineraries) {
        ModelMapper mapper = new ModelMapper();
        List<Itinerary> list = new ArrayList<>();
        for (ItineraryDTO itineraryDTO : itineraries) {
            list.add(mapper.map(itineraryDTO, Itinerary.class));
        }
        return itineraryRepository.saveAll(list);
    }
    public List<Itinerary> getItineraries(String tour_id) {
        return itineraryRepository.findAllByTourId(tour_id);
    }
}