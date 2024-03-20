package com.traveloveapi.DTO.service_package.create_package;

import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreatePackageBranchLimit {
    private ArrayList<GroupOptionDTO> set;
    private int limit;
    private int limit_special;
}
