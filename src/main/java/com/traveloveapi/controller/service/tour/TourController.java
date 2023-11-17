package com.traveloveapi.controller.service.tour;

import com.traveloveapi.DTO.service.TourDetailDTO;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.tour.TourService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service/tour")
public class TourController {
    final private TourService tourService;
    @PostMapping("/new")
    public TourDetailDTO createTour(@RequestParam MultipartFile[] files, @RequestParam String title, @RequestParam String description, @RequestParam String highlight, @RequestParam String note) {
        return tourService.createNewTour(title, description, highlight, note, files);
    }
}
