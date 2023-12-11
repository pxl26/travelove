package com.traveloveapi.controller.service.tour;

import com.traveloveapi.DTO.service.CreateTourDTO;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.constrain.ServiceType;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service/tour")
public class TourController {
    final private TourService tourService;
    @PostMapping("/new")
    @Tag(name = "SPRINT 2: Create a service by Service-owner")
    public ServiceDetailDTO createTour(@RequestBody CreateTourDTO data) {
        return tourService.createNewService(data.getService_type(), data.getTitle(), data.getDescription(), data.getHighlight(), data.getNote(), data.getFiles());
    }
}
