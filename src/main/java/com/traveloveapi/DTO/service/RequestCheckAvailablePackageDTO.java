package com.traveloveapi.DTO.service;

import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestCheckAvailablePackageDTO {
    private int month;
    private int year;
    private String service_id;
    private ArrayList<GroupOptionDTO> option_list;
}
