package com.traveloveapi.service.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.DTO.voucher.VoucherDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.collection.CollectionDetailEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherEntity;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.repository.bill.BillVoucherRepository;
import com.traveloveapi.repository.collection.CollectionDetailRepository;
import com.traveloveapi.repository.voucher.VoucherRedeemRepository;
import com.traveloveapi.repository.voucher.VoucherRepository;
import com.traveloveapi.service.collection.CollectionService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.JwtProvider;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    final private UserService userService;
    final private VoucherRepository voucherRepository;
    final private VoucherRedeemRepository voucherRedeemRepository;
    final private BillRepository billRepository;
    final private CollectionService collectionService;
    final private BillVoucherRepository billVoucherRepository;

    public ArrayList<VoucherDTO> getUsableVoucher(String tour_id) {
        return voucherRepository.getAvailableVoucherForTour(tour_id, SecurityContext.getUserID());
    }
    public float applyVouchers(String[] redeem_key_list, String bill_id) {
        ArrayList<String> key_list = new ArrayList<>();
        //-------REMOVE DUPLICATE VOUCHER-------
        for (String key: redeem_key_list)
        {
            boolean isExist = false;
            for (String ele: key_list)
                if (key.equals(ele))
                {
                    isExist = true;
                    break;
                }
            if (!isExist)
                key_list.add(key);
        }
        //----------DECODE KEY TO VOUCHER ID--------------
        ArrayList<String> voucher_list = new ArrayList<>();
        for (String ele: key_list)
            voucher_list.add(JwtProvider.getVoucherFromKey(ele));
        //---------------
        for (String key: key_list) {
            VoucherRedeemEntity voucherRedeem = voucherRedeemRepository.findByKey(key);
            if (voucherRedeem.getStatus()==VoucherRedeemStatus.USED)
                throw new CustomException("Voucher was used: #key_"+voucherRedeem.getRedeem_key(), 400);
            if (voucherRedeem.getStatus()==VoucherRedeemStatus.EXPIRED)
                throw new CustomException("Expired voucher", 400);

            voucherRedeem.setStatus(VoucherRedeemStatus.USED);
            voucherRedeemRepository.update(voucherRedeem);
        }


        BillEntity bill = billRepository.find(bill_id);
        float bill_amount = bill.getTotal();
        for (String voucher_id: voucher_list) {
            float discount_by_voucher = 0;
            VoucherEntity voucher = voucherRepository.find(voucher_id);

            //--------VALIDATED-------
            if(!validateVoucher(voucher, bill.getService_id()))
                throw new CustomException("Voucher cannot be apply for this tour: #voucher-" + voucher_id, 400);
            if (bill.getTotal()<voucher.getUnder_applied())
                throw new CustomException("Bill amount can not reach voucher condition: #"+voucher_id, 400);
            //-----------

            if (voucher.getDiscount_type()==VoucherDiscountType.FIXED)
                discount_by_voucher=voucher.getFixed_discount();
            else if (voucher.getDiscount_type()==VoucherDiscountType.PERCENT)
                discount_by_voucher = Math.min(bill.getTotal() * voucher.getPercent_discount() / 100, voucher.getMax_discount());

            //----------------------------------------------
            if (discount_by_voucher >= bill_amount)            // bill cannot be less than 0 by voucher discount
                discount_by_voucher = bill_amount;
            BillVoucherEntity bill_voucher = new BillVoucherEntity();
            bill_voucher.setVoucher_id(voucher_id);
            bill_voucher.setBill_id(bill_id);
            bill_voucher.setDiscount_amount(discount_by_voucher);
            billVoucherRepository.save(bill_voucher);

            bill_amount-=discount_by_voucher;
            if (bill_amount==0)
                break;
        }
        //-----DISABLE WAS USED

        bill.setTotal(bill_amount);
        billRepository.update(bill);
        return bill_amount;
    }

    public ArrayList<RedeemVoucherDTO> getVoucherByUser(String user_id, int page) {
        if (user_id!=null)
            if (!userService.isAdmin() && !user_id.equals(SecurityContext.getUserID()))
                throw new ForbiddenException();
        if (user_id==null)
            user_id=SecurityContext.getUserID();
        return voucherRedeemRepository.getByUser(user_id, page);
    }

    public VoucherRedeemEntity redeemVoucher(String voucher_id) {
        if (!voucherRepository.getVoucher(SecurityContext.getUserID(), voucher_id).isEmpty())
            throw new CustomException("Only redeem one time", 400);
        VoucherEntity voucher = voucherRepository.find(voucher_id);
        if (voucher==null)
            throw new CustomException("Voucher ID not found", 404);
        if (voucher.getStock()<1)
            throw new CustomException("Out of stock", 400);
        if (voucher.getEnd_at().getTime()<System.currentTimeMillis())
            throw new CustomException("Promotion was ended",400);
        if (voucher.getStatus()!=VoucherStatus.VERIFIED)
            throw new CustomException("Invalid voucher", 400);

        VoucherRedeemEntity voucherRedeem = new VoucherRedeemEntity();
        voucherRedeem.setId(UUID.randomUUID().toString());
        voucherRedeem.setVoucher_id(voucher_id);
        voucherRedeem.setUser_id(SecurityContext.getUserID());
        voucherRedeem.setRedeem_at(new Timestamp(System.currentTimeMillis()));
        voucherRedeem.setExpire_at(new Timestamp(voucherRedeem.getRedeem_at().getTime() + voucher.getExpiration()* 86400000L));    // Ex:expiration = 1 = 1 day
        voucherRedeem.setRedeem_key(JwtProvider.generateVoucherKey(voucher_id, voucherRedeem.getExpire_at().getTime()));
        voucherRedeem.setStatus(VoucherRedeemStatus.AVAILABLE);

        voucherRedeemRepository.save(voucherRedeem);
        voucher.setStock(voucher.getStock()-1);
        voucherRepository.update(voucher);

        return voucherRedeem;
    }
    public VoucherEntity createVoucher(String code, String title, int stock, Timestamp start_at, Timestamp end_at, String detail, VoucherDiscountType discount_type, float fixed_discount, float percent_discount, float under_applied, VoucherTargetType target_type, String target_id, Currency currency, int expiration, float max_discount) {
        if (target_type==VoucherTargetType.TOUR)
        {
            if (!userService.verifyIsOwner(target_id, SecurityContext.getUserID()) && !userService.isAdmin())
                throw new ForbiddenException();
        }
        else if (!userService.isAdmin())
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
        voucher.setMax_discount(max_discount);

        voucherRepository.save(voucher);
        return voucher;
    }


    public ArrayList<VoucherEntity> getVoucherAvailable(String tour_id, String user_id) {
        return null;
    }

    public VoucherEntity verifyVoucher(String voucher_id) {
        VoucherEntity voucher = voucherRepository.find(voucher_id);
        voucher.setStatus(VoucherStatus.VERIFIED);
        voucherRepository.update(voucher);
        return voucher;
    }

    private boolean validateVoucher(VoucherEntity voucher, String tour_id) {
        if (voucher.getTarget_type()==VoucherTargetType.ALL)
            return true;
        if (voucher.getTarget_type()==VoucherTargetType.TOUR)
            return voucher.getTarget_id().equals(tour_id);
        return collectionService.isHave(voucher.getTarget_id(), tour_id);     //collection validation
    }
}