package com.traveloveapi.schedule;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.repository.service_package.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class BillJobScheduler {
    final private BillRepository billRepository;

    @Scheduled(fixedDelay = 60000)
    public void cancelExpiredBill() {
        ArrayList<BillEntity> bill_list =  billRepository.getAllPending();
        if (bill_list.isEmpty())
            return;
        for (BillEntity bill: bill_list) {
            System.out.println(System.currentTimeMillis());
            System.out.println(bill.getCreate_at().getTime());
            if (System.currentTimeMillis() - bill.getCreate_at().getTime() > 900000)
            {
                bill.setStatus(BillStatus.CANCELED);
                bill.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                billRepository.update(bill);
            }

        }
    }
}
