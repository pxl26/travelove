package com.traveloveapi.DTO.service_package;

import com.traveloveapi.DTO.service_package.bill.CreateBillPersonType;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill_detail_option.BillDetailOptionEntity;
import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
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
    private Double total;
    private Timestamp update_at;
    private ArrayList<GroupOptionDTO> options;
    private ArrayList<CreateBillPersonType> person_types;
    private BillStatus status;
    private String gateway_url;
    private PayMethod pay_method;
    private String originCurrency;
    private String userCurrency;
}
