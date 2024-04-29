package com.traveloveapi.mq;

import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.repository.notification.NotificationRepository;
import com.traveloveapi.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "feedback")
@AllArgsConstructor
public class FeedbackReceiver {
    final private NotificationRepository notificationRepository;
    final private EventService eventService;

    @RabbitHandler
    public void process(FeedbackEntity feedback) {
        notificationRepository.save(eventService.feedback(feedback));
    }
}
