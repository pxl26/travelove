package com.traveloveapi.service.voucher;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.voucher.VoucherRepository;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.JwtProvider;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    final private UserService userService;
    final private VoucherRepository voucherRepository;

    public VoucherRedeemEntity redeemVoucher(String voucher_id) {
        if (!voucherRepository.getVoucher(SecurityContext.getUserID(), voucher_id).isEmpty())
            throw new CustomException("Only redeem one time", 400);
        VoucherEntity voucher = voucherRepository.find(voucher_id);
        if (voucher.getStock()<1)
            throw new CustomException("Out of stock", 400);
        if (voucher.getEnd_at().getTime()<System.currentTimeMillis())
            throw new CustomException("Promotion was ended",400);

        VoucherRedeemEntity voucherRedeem = new VoucherRedeemEntity();
        voucherRedeem.setId(UUID.randomUUID().toString());
        voucherRedeem.setVoucher_id(voucher_id);
        voucherRedeem.setUser_id(SecurityContext.getUserID());
        voucherRedeem.setRedeem_at(new Timestamp(System.currentTimeMillis()));
        voucherRedeem.setExpire_at(new Timestamp(voucherRedeem.getRedeem_at().getTime() + voucher.getExpiration()* 86400000L));    // Ex:expiration = 1 = 1 day
        voucherRedeem.setKey(JwtProvider.generateVoucherKey(voucher_id, voucherRedeem.getExpire_at().getTime()));

        return voucherRedeem;
    }
    public VoucherEntity createVoucher(String code, String title, int stock, Timestamp start_at, Timestamp end_at, String detail, VoucherDiscountType discount_type, float fixed_discount, float percent_discount, float under_applied, VoucherTargetType target_type, String target_id, Currency currency, int expiration) {
        if (!userService.isAdmin() && !(target_type ==VoucherTargetType.TOUR) && !userService.verifyIsOwner(target_id, SecurityContext.getUserID()))
            throw new ForbiddenException();
        //------------------
        VoucherEntity voucher = new VoucherEntity();
        voucher.setStatus(userService.isAdmin() ? VoucherStatus.VERIFIED : VoucherStatus.PENDING);
        voucher.setId(UUID.randomUUID().toString());
        voucher.setCode(code);
        voucher.setTitle(title);
        voucher.setStock(stock);
        voucher.setCreator_id(SecurityContext.getUserID());
        voucher.setCurrency(currency);
        voucher.setDetail(detail);
        voucher.setExpiration(expiration);
        voucher.setDiscount_type(discount_type);
        voucher.setTarget_type(target_type);
        voucher.setTarget_id(target_id);
        voucher.setStart_at(start_at);
        voucher.setEnd_at(end_at);
        voucher.setUnder_applied(under_applied);
        voucher.setFixed_discount(fixed_discount);
        voucher.setPercent_discount(percent_discount);

        voucherRepository.save(voucher);
        return voucher;
    }

    public VoucherEntity verifyVoucher(String voucher_id) {
        VoucherEntity voucher = voucherRepository.find(voucher_id);
        voucher.setStatus(VoucherStatus.VERIFIED);
        voucherRepository.update(voucher);
        return voucher;
    }
}
