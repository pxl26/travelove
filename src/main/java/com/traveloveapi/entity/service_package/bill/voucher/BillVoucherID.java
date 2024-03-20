package com.traveloveapi.entity.service_package.bill.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillVoucherID implements Serializable {
    private String bill_id;
    private String voucher_id;
}
