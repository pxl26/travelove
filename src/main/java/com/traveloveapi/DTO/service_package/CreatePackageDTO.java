package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.create_package.CreatePackageBranchLimit;
import com.traveloveapi.DTO.service_package.create_package.CreatePackageGroup;
import com.traveloveapi.DTO.service_package.create_package.CreatePackagePersonType;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CreatePackageDTO {
    private String service_id;
    private ArrayList<CreatePackageGroup> package_group;
    private ArrayList<GroupOptionDTO> disable_list;
    private ArrayList<CreatePackageBranchLimit> branch_limit;
    private ArrayList<CreatePackagePersonType> person_type;
}