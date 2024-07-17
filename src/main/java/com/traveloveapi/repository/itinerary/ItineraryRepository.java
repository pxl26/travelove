package com.traveloveapi.repository.itinerary;

import com.traveloveapi.entity.itinerary.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, String> {
    public List<Itinerary> findAllByTourId(String tourId);
}
