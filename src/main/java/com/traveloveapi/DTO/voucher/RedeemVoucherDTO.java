package com.traveloveapi.DTO.voucher;

import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherStatus;
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
}
