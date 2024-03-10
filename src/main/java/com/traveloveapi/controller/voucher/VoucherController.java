package com.traveloveapi.controller.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.service.voucher.VoucherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherController {
    final private VoucherService voucherService;

    @PutMapping("/verify")
    @Tag(name = "SPRINT 9")
    public VoucherEntity verifyVoucher(@RequestParam String voucher_id) {
        return voucherService.verifyVoucher(voucher_id);
    }

    @PostMapping
    @Tag(name = "SPRINT 9")
    public VoucherEntity createVoucher(String code, String title, int stock, Date start_at, Date end_at, String detail, VoucherDiscountType discount_type, float fixed_discount, float percent_discount, float under_applied, float max_discount, VoucherTargetType target_type, String target_id, Currency currency, int expiration) {
        return voucherService.createVoucher(code, title, stock, new Timestamp(start_at.getTime()), new Timestamp(end_at.getTime()), detail, discount_type, fixed_discount, percent_discount, under_applied, target_type, target_id, currency, expiration, max_discount);
    }

    @PostMapping("/redeem")
    @Tag(name = "SPRINT 9")
    public VoucherRedeemEntity redeemVoucher(@RequestParam String voucher_id) {
        return voucherService.redeemVoucher(voucher_id);
    }

    @GetMapping
    @Tag(name = "SPRINT 9")
    public ArrayList<RedeemVoucherDTO> getAllVoucher(@RequestParam(required = false) String user_id, @RequestParam int page) {
        return voucherService.getVoucherByUser(user_id, page);
    }
}
