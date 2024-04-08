package com.traveloveapi.DTO.voucher;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherDTO {
    private String voucher_id;
    private VoucherDiscountType type;
    private float minimum_spend;
    private float fixed_discount;
    private float percent_discount;
    private float max_discount;
    private Currency currency;
    private VoucherTargetType target_type;
    private String target_id;
}
