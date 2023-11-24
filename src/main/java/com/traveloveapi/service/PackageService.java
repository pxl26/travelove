package com.traveloveapi.service;

import com.traveloveapi.DTO.service_package.BanListOptionDTO;
import com.traveloveapi.DTO.service_package.CreatePackageDTO;
import com.traveloveapi.DTO.service_package.create_package.CreatePackageBranchLimit;
import com.traveloveapi.DTO.service_package.create_package.CreatePackageGroup;
import com.traveloveapi.DTO.service_package.create_package.CreatePackageOption;
import com.traveloveapi.DTO.service_package.create_package.CreatePackagePersonType;
import com.traveloveapi.entity.service_package.disable_option.DisableOptionEntity;
import com.traveloveapi.entity.service_package.limit.PackageLimitEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeEntity;
import com.traveloveapi.repository.service_package.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PackageService {
    final private PackageGroupRepository packageGroupRepository;
    final private PackageOptionRepository packageOptionRepository;
    final private PackageDisableOptionRepository packageDisableOptionRepository;
    final private PackageLimitRepository packageLimitRepository;
    final private PackagePersonTypeRepository packagePersonTypeRepository;
    final private BillDetailOptionRepository billDetailOptionRepository;
    final private BillDetailPersonTypeRepository billDetailPersonTypeRepository;
    final private BillRepository billRepository;

    public void addPackage(CreatePackageDTO data) {
        String service_id = data.getService_id();

        ArrayList<CreatePackageGroup> groupList = data.getPackage_group();
        for (CreatePackageGroup groups: groupList) {
            PackageGroupEntity group = new PackageGroupEntity(service_id, groups.getGroup_number(), groups.getTitle(), groups.getLimit());
            packageGroupRepository.save(group);
            for (CreatePackageOption options: groups.getPackage_option()) {
                PackageOptionEntity option = getPackageOptionEntity(groups, options, service_id);
                packageOptionRepository.save(option);
            }
        }

        ArrayList<BanListOptionDTO> banList = data.getDisable_list();
        for (BanListOptionDTO ban: banList) {
            DisableOptionEntity disableEntity = new DisableOptionEntity();
            disableEntity.setService_id(service_id);
            disableEntity.setGroup_1(ban.getBan_list().get(0).getGroup_number());
            disableEntity.setOption_1(ban.getBan_list().get(0).getOption_number());
            disableEntity.setGroup_2(ban.getBan_list().get(1).getGroup_number());
            disableEntity.setOption_2(ban.getBan_list().get(1).getOption_number());
            packageDisableOptionRepository.save(disableEntity);
        }

        ArrayList<CreatePackageBranchLimit> branchLimitList = data.getBranch_limit();
        for (CreatePackageBranchLimit branch: branchLimitList) {
            PackageLimitEntity entity = getPackageLimitEntity(branch, service_id);
            packageLimitRepository.save(entity);
        }

        ArrayList<CreatePackagePersonType> personTypes = data.getPerson_type();
        for (CreatePackagePersonType type: personTypes) {
            PackagePersonTypeEntity entity = new PackagePersonTypeEntity();
            entity.setService_id(service_id);
            entity.setName(type.getName());
            entity.setType_number(type.getType_number());
            entity.setBonus_price(type.getBonus_price());
            packagePersonTypeRepository.save(entity);
        }
    }

    private static PackageLimitEntity getPackageLimitEntity(CreatePackageBranchLimit branch, String service_id) {
        PackageLimitEntity entity = new PackageLimitEntity();
        entity.setService_id(service_id);
        entity.setLimit(branch.getLimit());
        entity.setLimit_special(branch.getLimit_special());
        entity.setGroup_1(branch.getSet().get(0).getGroup_number());
        entity.setOption_1(branch.getSet().get(0).getOption_number());
        entity.setGroup_2(branch.getSet().get(1).getGroup_number());
        entity.setOption_2(branch.getSet().get(1).getOption_number());
        return entity;
    }

    private static PackageOptionEntity getPackageOptionEntity(CreatePackageGroup groups, CreatePackageOption options, String service_id) {
        PackageOptionEntity option = new PackageOptionEntity();
        option.setService_id(service_id);
        option.setName(options.getName());
        option.setOption_number(options.getOption_number());
        option.setGroup_number(groups.getGroup_number());
        option.setPrice(options.getPrice());
        option.setPrice_special(options.getPrice_special());
        option.setLimit(options.getLimit());
        option.setLimit_special(options.getLimit_special());
        return option;
    }
}
