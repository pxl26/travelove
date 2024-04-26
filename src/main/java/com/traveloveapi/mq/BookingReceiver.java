package com.traveloveapi.mq;

import com.traveloveapi.entity.NotificationEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.repository.notification.NotificationRepository;
import com.traveloveapi.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "booking")
@AllArgsConstructor
public class BookingReceiver {
    final private EventService eventService;
    final private NotificationRepository notificationRepository;

    @RabbitHandler
    public void process(String bill_id) {
        notificationRepository.save(eventService.bookTour(bill_id));
    }
}
