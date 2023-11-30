package com.traveloveapi.controller.service.tour.package_service;

import com.traveloveapi.DTO.service_package.CheckAvailableRequest;
import com.traveloveapi.DTO.service_package.CreatePackageDTO;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service/package")
@RequiredArgsConstructor
public class ServicePackageController {
    final private PackageService packageService;
    final private BillService billService;
    @PostMapping
    public void addPackage(@RequestBody CreatePackageDTO data) {
        packageService.addPackage(data);
    }

    @GetMapping("/check")
    public int checkByDate(@RequestBody CheckAvailableRequest request) {
        return billService.getAvailablePackage(request.getService_id(), request.getDate(), request.getOptions());
    }
}
