package com.traveloveapi.controller.service.tour;

import com.traveloveapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service/tour")
public class TourController {
    final private UserRepository userRepository;
    @PostMapping("/new")
    public String createTour() {
        return null;
    }
}
