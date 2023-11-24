package com.traveloveapi.DTO.service_package;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanListOptionDTO {
    private ArrayList<GroupOptionDTO> ban_list;
}
