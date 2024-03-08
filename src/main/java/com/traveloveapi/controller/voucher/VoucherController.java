package com.traveloveapi.controller.voucher;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherController {
    final private VoucherService voucherService;

    @PutMapping("/verify")
    public VoucherEntity verifyVoucher(@RequestParam String voucher_id) {
        return voucherService.verifyVoucher(voucher_id);
    }

    @PostMapping
    public VoucherEntity createVoucher(String code, String title, int stock, Timestamp start_at, Timestamp end_at, String detail, VoucherDiscountType discount_type, float fixed_discount, float percent_discount, float under_applied, float max_discount, VoucherTargetType target_type, String target_id, Currency currency, int expiration) {
        return voucherService.createVoucher(code, title, stock, start_at, end_at, detail, discount_type, fixed_discount, percent_discount, under_applied, target_type, target_id, currency, expiration);
    }

    @PostMapping("/redeem")
    public VoucherRedeemEntity redeemVoucher(@RequestParam String voucher_id) {
        return voucherService.redeemVoucher(voucher_id);
    }
}
