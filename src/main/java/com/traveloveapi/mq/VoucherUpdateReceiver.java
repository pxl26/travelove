package com.traveloveapi.mq;

import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.repository.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "voucher-update")
@AllArgsConstructor
public class VoucherUpdateReceiver {
    final private VoucherRepository voucherRepository;

    @RabbitHandler
    private void handle(VoucherEntity entity) {
        entity.setStock(entity.getStock() - 1);
        voucherRepository.save(entity);
    }
}
