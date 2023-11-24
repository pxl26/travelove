package com.traveloveapi.DTO.service_package.create_package;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CreatePackageGroup {
    private int group_number;
    private String title;
    private int limit;
    private ArrayList<CreatePackageOption> package_option;
}
