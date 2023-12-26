package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.create_package.CreatePackageBranchLimit;
import com.traveloveapi.DTO.service_package.create_package.CreatePackageGroup;
import com.traveloveapi.DTO.service_package.create_package.CreatePackagePersonType;
import com.traveloveapi.DTO.service_package.create_package.CreateSpecialOption;
import com.traveloveapi.entity.service_package.special_date.SpecialDateEntity;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CreatePackageDTO {
    private String service_id;
    private ArrayList<CreatePackageGroup> package_group;
    private ArrayList<ArrayList<GroupOptionDTO>> disable_list;
    private ArrayList<CreatePackageBranchLimit> branch_limit;
    private ArrayList<CreatePackagePersonType> person_type;
    private ArrayList<SpecialDateDTO> special_date_list;
    private ArrayList<CreateSpecialOption> special_option;
    private float min_price;
}