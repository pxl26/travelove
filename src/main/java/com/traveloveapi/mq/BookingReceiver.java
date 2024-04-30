package com.traveloveapi.mq;

import com.traveloveapi.repository.notification.NotificationRepository;
import com.traveloveapi.service.EventService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "booking")
@AllArgsConstructor
@Component
public class BookingReceiver {
    final private EventService eventService;
    final private NotificationRepository notificationRepository;

    @RabbitHandler
    public void process(String bill_id) {
        notificationRepository.save(eventService.bookTour(bill_id));
    }
}
