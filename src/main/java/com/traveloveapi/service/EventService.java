package com.traveloveapi.service;

import com.traveloveapi.constrain.voucher.NotificationType;
import com.traveloveapi.entity.NotificationEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.bill.BillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventService {
    final private ServiceRepository serviceRepository;
    final private BillRepository billRepository;

    public NotificationEntity bookTour(String bill_id) {
        BillEntity bill = billRepository.find(bill_id);
        NotificationEntity notificationEntity = new NotificationEntity();
        ServiceEntity tour = serviceRepository.find(bill.getService_id());
        notificationEntity.setId(UUID.randomUUID().toString());
        notificationEntity.setCreated_at(new Timestamp(System.currentTimeMillis()));
        notificationEntity.setImage(tour.getThumbnail());
        notificationEntity.setContent("Nhận <br>" + bill.getQuantity() + " vé mới</br> cho ngày <br>"+ bill.getDate() + "</br> - "+tour.getTitle());
        notificationEntity.setConsumer_id(tour.getService_owner());
        notificationEntity.setType(NotificationType.NEW_BOOKING);
        notificationEntity.setUrl("/bill?bill_id=" + bill.getId());

        return notificationEntity;
    }

    public NotificationEntity cancelBooking(BillEntity bill) {
        NotificationEntity notificationEntity = new NotificationEntity();
        ServiceEntity tour = serviceRepository.find(bill.getService_id());
        notificationEntity.setId(UUID.randomUUID().toString());
        notificationEntity.setCreated_at(new Timestamp(System.currentTimeMillis()));
        notificationEntity.setImage(tour.getThumbnail());
        notificationEntity.setContent("Có <br>" + bill.getQuantity() + " vé đã hủy</br> cho ngày <br>"+ bill.getDate()+"</br> - "+tour.getTitle());
        notificationEntity.setConsumer_id(tour.getService_owner());
        notificationEntity.setType(NotificationType.CANCEL_BOOKING);
        notificationEntity.setUrl("/bill?bill_id=" + bill.getId());

        return notificationEntity;
    }


    public NotificationEntity feedback(FeedbackEntity feedback) {
        NotificationEntity notificationEntity = new NotificationEntity();
        ServiceEntity tour = serviceRepository.find(feedback.getRef_id());
        notificationEntity.setId(UUID.randomUUID().toString());
        notificationEntity.setCreated_at(new Timestamp(System.currentTimeMillis()));
        notificationEntity.setImage(tour.getThumbnail());
        notificationEntity.setContent("Nhận đánh giá <br>" + feedback.getRating()+" sao</br>" + (feedback.getContent()!=null ? ("và bình luận " + (feedback.isHas_media() ? "kèm video hoặc hình ảnh": "")): (feedback.isHas_media() ? "kèm video hoặc hình ảnh":"")));
        notificationEntity.setConsumer_id(tour.getService_owner());
        notificationEntity.setType(NotificationType.NEW_FEEDBACK);
        notificationEntity.setUrl("/tour?tour_id=" + feedback.getRef_id()+"&feedback_id="+feedback.getRef_id());

        return notificationEntity;
    }
}
