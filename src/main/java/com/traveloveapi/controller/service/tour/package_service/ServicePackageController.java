package com.traveloveapi.controller.service.tour.package_service;

import com.traveloveapi.DTO.service_package.CreatePackageDTO;
import com.traveloveapi.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/package")
@RequiredArgsConstructor
public class ServicePackageController {
    final private PackageService packageService;
    @PostMapping
    public void addPackage(@RequestBody CreatePackageDTO data) {
        packageService.addPackage(data);
    }
}
