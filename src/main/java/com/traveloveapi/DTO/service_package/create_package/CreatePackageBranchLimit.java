package com.traveloveapi.DTO.service_package.create_package;

import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CreatePackageBranchLimit {
    private ArrayList<GroupOptionDTO> set;
    private int remain;
}
