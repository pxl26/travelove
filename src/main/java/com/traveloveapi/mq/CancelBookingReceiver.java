package com.traveloveapi.mq;

import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.repository.notification.NotificationRepository;
import com.traveloveapi.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "cancel-booking")
@AllArgsConstructor
public class CancelBookingReceiver {
    final private NotificationRepository notificationRepository;
    final private EventService eventService;

    @RabbitHandler
    public void process(BillEntity bill) {
        notificationRepository.save(eventService.cancelBooking(bill));
    }
}
