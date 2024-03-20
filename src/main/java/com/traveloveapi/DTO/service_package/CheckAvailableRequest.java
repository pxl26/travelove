package com.traveloveapi.DTO.service_package;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAvailableRequest {
    private String service_id;
    private Date date;
    private ArrayList<GroupOptionDTO> options;
}
