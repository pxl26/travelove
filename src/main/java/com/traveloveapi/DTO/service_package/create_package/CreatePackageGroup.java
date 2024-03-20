package com.traveloveapi.DTO.service_package.create_package;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreatePackageGroup {
    private int group_number;
    private String title;
    private int limit;
    private int limit_special;
    private ArrayList<CreatePackageOption> package_option;
}
