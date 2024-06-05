package com.traveloveapi.controller.activity;

import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.entity.logging.ActivityLogEntity;
import com.traveloveapi.service.logging.ActivityLoggingService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class UserActivityController {
    final private ActivityLoggingService activityLoggingService;

    @GetMapping
    @Tag(name = "SPRINT 4")
    @Operation(description = "Show activity logs", hidden = true)
    public ArrayList<ActivityLogEntity> getMyActivityLog(@RequestParam int page) {
        return activityLoggingService.getMyActivityLog(page);
    }

    @GetMapping("/viewed-tour")
    @Tag(name = "SPRINT 4")
    public ArrayList<ViewedTourDTO> getViewedTour(@RequestParam(required = false) String currency) {
        return activityLoggingService.getViewedTour(SecurityContext.getUserID(), currency);
    }
}
