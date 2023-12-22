package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.bill.CreateBillPersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    private String service_id;
    private ArrayList<GroupOptionDTO> options;
    private Date date;
    private ArrayList<CreateBillPersonType> person_types;
}
