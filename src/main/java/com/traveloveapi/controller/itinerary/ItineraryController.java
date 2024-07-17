package com.traveloveapi.controller.itinerary;

import com.traveloveapi.DTO.itinerary.ItineraryDTO;
import com.traveloveapi.DTO.itinerary.ItineraryRequest;
import com.traveloveapi.entity.itinerary.Itinerary;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.service.itinerary.ItineraryService;
import com.traveloveapi.service.local_file.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;
    private final S3FileService s3FileService;

    @PostMapping
    @Tag(name = "Itinerary")
    public List<Itinerary> createNew(@RequestParam String tour_id, @RequestParam String[] descriptionList,@RequestParam String[] timeList, @RequestParam MultipartFile[] files) {
        List<ItineraryDTO> list = new ArrayList<>();
        for (int i=0; i< descriptionList.length; i++) {
            ItineraryDTO itinerary = new ItineraryDTO();
            itinerary.setContent(descriptionList[i]);
            itinerary.setTime(timeList[i]);
            itinerary.setTourId(tour_id);
            itinerary.setMedia(s3FileService.uploadFile(files[i], "public/service/" + tour_id + "/itinerary", UUID.randomUUID().toString()));
            itinerary.setSequence(i);
            list.add(itinerary);
        }
        return itineraryService.createNewItinerary(list);
    }
}
