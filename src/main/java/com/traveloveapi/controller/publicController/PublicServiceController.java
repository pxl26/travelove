package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/service")
public class PublicServiceController {
    final private TourService tourService;
    @GetMapping("/tour")
    @Tag(name = "SPRINT 2: View tour by everyone")
    public ServiceDetailDTO getTour(@RequestParam String id) {
        return tourService.getTour(id);
    }
}
