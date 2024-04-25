package com.traveloveapi.DTO.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherDiscountSummary {
    private Double amount;
    private String voucher_id;
    private String voucher_title;
}
