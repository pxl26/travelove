package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.create_package.CreatePackageGroup;
import com.traveloveapi.DTO.service_package.create_package.CreatePackagePersonType;
import com.traveloveapi.DTO.service_package.create_package.CreateSpecialOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageInfoDTO {
    private String service_id;
    private ArrayList<PackageGroupDTO> package_group;
    private ArrayList<ArrayList<GroupOptionDTO>> disable_list;
    private ArrayList<CreatePackagePersonType> peron_type;
    private ArrayList<CreateSpecialOption> special_option;
    private ArrayList<SpecialDateDTO> special_date;
}