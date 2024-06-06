package com.traveloveapi.controller.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.DTO.voucher.VoucherDTO;
import com.traveloveapi.constrain.voucher.VoucherAuditAction;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.service.voucher.VoucherService;
import com.traveloveapi.utility.SecurityContext;
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
    @Tag(name = "SPRINT 9 - MANAGE")
    public VoucherEntity verifyVoucher(@RequestParam String voucher_id, @RequestParam VoucherAuditAction action) {
        return voucherService.verifyVoucher(voucher_id, action);
    }

    @PostMapping
    @Tag(name = "SPRINT 9 - MANAGE")
    public VoucherEntity createVoucher(String code, String title, Integer stock, Date start_at, Date end_at, String detail, VoucherDiscountType discount_type, Float fixed_discount, Float percent_discount, Float minimum_spend, Float max_discount, VoucherTargetType target_type, String target_id, String currency, Integer expiration) {
        return voucherService.createVoucher(code, title, stock, new Timestamp(start_at.getTime()), new Timestamp(end_at.getTime()), detail, discount_type, fixed_discount, percent_discount, minimum_spend, target_type, target_id, currency, expiration, max_discount);
    }

    @PostMapping("/redeem")
    @Tag(name = "SPRINT 9")
    public VoucherRedeemEntity redeemVoucher(@RequestParam String voucher_id) {
        return voucherService.redeemVoucher(voucher_id, SecurityContext.getUserID(),false);
    }

    @GetMapping("/my-voucher")
    @Tag(name = "SPRINT 9")
    public ArrayList<RedeemVoucherDTO> getAllVoucher(@RequestParam(required = false) String user_id, @RequestParam int page, @RequestParam(required = false) String currency) {
        return voucherService.getVoucherByUser(user_id, page, currency);
    }

    @GetMapping("/tour")
    @Tag(name = "SPRINT 9")
    public ArrayList<VoucherDTO> getByTour(@RequestParam String tour_id, @RequestParam(required = false) String currency) {
        return voucherService.getUsableVoucher(tour_id, currency);
    }

    @GetMapping("/manage")
    @Tag(name = "SPRINT 9 - MANAGE")
    public ArrayList<VoucherEntity> getVoucherByTourOwner(@RequestParam(required = false) String creator,@RequestParam(required = false) VoucherTargetType type, @RequestParam(required = false) String target_id) {
        return voucherService.getVoucherByCreator(creator, type, target_id);
    }

    @PostMapping("/give-voucher")
    @Tag(name = "SPRINT 9 - MANAGE")
    public VoucherRedeemEntity giveVoucher(@RequestParam String voucher_id, @RequestParam String redeem_user) {
        return voucherService.giveVoucher(voucher_id, redeem_user);
    }

    @GetMapping("/pending-voucher")
    @Tag(name = "SPRINT 9 - MANAGE")
    public ArrayList<VoucherEntity> getAllPendingVoucher() {
        return voucherService.getAllPendingVoucher();
    }

}
