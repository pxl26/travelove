package com.traveloveapi.DTO.voucher;

import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class RedeemVoucherDTO {
    private String voucher_id;
    private String redeem_key;
    private String title;
    private String detail;
    private VoucherRedeemStatus status;
    private Timestamp redeem_at;
    private Timestamp expire_at;
    private VoucherTargetType target_type;
    private String target_id;


    private VoucherDiscountType discount_type;
    private float fixed_discount;
    private float percent_discount;
    private float minimum_spend;
    private float max_discount;
    private String currency;
}
