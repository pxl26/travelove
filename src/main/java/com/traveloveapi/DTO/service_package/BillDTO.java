package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.bill.CreateBillPersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private String id;
    private String service_id;
    private String user_id;
    private Timestamp create_at;
    private float total;
    private ArrayList<GroupOptionDTO> options;
    private ArrayList<CreateBillPersonType> person_types;
}
