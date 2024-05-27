package com.traveloveapi.controller.service.tour.package_service;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.service_package.CheckAvailableRequest;
import com.traveloveapi.DTO.service_package.CreatePackageDTO;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.PackageService;
import com.traveloveapi.service.redis.RedisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service/package")
@RequiredArgsConstructor
public class ServicePackageController {
    final private PackageService packageService;
    final private BillService billService;
    final private RedisService redisService;

    @PostMapping
    @Tag(name = "SPRINT 2")
    public SimpleResponse addPackage(@RequestBody CreatePackageDTO data) {
        packageService.addPackage(data);
        redisService.getConnection().del("tour_detail:"+data.getService_id());
        redisService.getConnection().del("tour_detail:"+data.getService_id()+":privilege");
        return new SimpleResponse("Done", 200);
    }

    @Tag(name = "SPRINT 2")
    @GetMapping("/check")
    public int checkByDate(@RequestBody CheckAvailableRequest request) {
        return billService.getAvailablePackage(request.getService_id(), request.getDate(), request.getOptions());
    }
}
