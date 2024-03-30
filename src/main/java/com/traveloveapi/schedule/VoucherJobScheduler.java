package com.traveloveapi.schedule;

import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import com.traveloveapi.repository.voucher.VoucherRedeemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class VoucherJobScheduler {
    final private VoucherRedeemRepository voucherRedeemRepository;

    @Scheduled(fixedDelay = 3600000)
    public void deleteMyVoucher() {
       voucherRedeemRepository.removeMyVoucher();
    }
}
