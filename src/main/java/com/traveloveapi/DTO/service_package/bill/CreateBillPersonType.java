package com.traveloveapi.DTO.service_package.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillPersonType {
    private String type;
    private int quantity;
}
