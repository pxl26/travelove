package com.traveloveapi.controller.admin;

import com.traveloveapi.constrain.SensorAction;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/service")
public class AdminServiceController {
    final private TourService tourService;
    @PutMapping("/tour/sensor")
    @Tag(name = "SPRINT 2: Sensor a tour by ADMIN")
    public ServiceEntity sensorTour(@RequestParam SensorAction action, @RequestParam String id) {
        return tourService.changeStatus(action, id);
    }
}
