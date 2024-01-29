package com.traveloveapi.controller.activity;

import com.traveloveapi.entity.logging.ActivityLogEntity;
import com.traveloveapi.service.logging.ActivityLoggingService;
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
    @Tag(name = "SPRINT 4: User side")
    @Operation(description = "Show viewed service")
    public ArrayList<ActivityLogEntity> getMyActivityLog(@RequestParam int page) {
        return activityLoggingService.getMyActivityLog(page);
    }
}
