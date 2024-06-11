package com.traveloveapi.DTO.voucher;

import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherDTO {
    private String voucher_id;
    private VoucherDiscountType type;
    private Double minimum_spend;
    private Double fixed_discount;
    private float percent_discount;
    private Double max_discount;
    private String originCurrency;
    private VoucherTargetType target_type;
    private String target_id;
    private String userCurrency;
}
