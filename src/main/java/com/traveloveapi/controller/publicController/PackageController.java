package com.traveloveapi.controller.publicController;

import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupId;
import com.traveloveapi.repository.service_package.PackageGroupRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/package")
@RequiredArgsConstructor
public class PackageController {
    final private PackageGroupRepository repository;

    @PutMapping
    public void newGroup(@RequestBody PackageGroupEntity entity) {
        repository.save(entity);
    }

    @GetMapping
    public PackageGroupEntity getGroup(PackageGroupId id) {
        return repository.find(id);
    }
}
