package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.service.TourDetailDTO;
import com.traveloveapi.service.tour.TourService;
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
    public TourDetailDTO getTour(@RequestParam String id) {
        return tourService.getTour(id);
    }
}
