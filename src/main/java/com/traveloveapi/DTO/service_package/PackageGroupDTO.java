package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.create_package.CreatePackageOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageGroupDTO {
    private int group_number;
    private String title;
    private ArrayList<OptionDTO> package_option;
}
