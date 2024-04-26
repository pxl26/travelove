package com.traveloveapi.controller.notification;

import com.traveloveapi.constrain.OwnerRegistrationStatus;
import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.NotificationEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.owner_registration.TourOwnerRegistrationEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.notification.NotificationRepository;
import com.traveloveapi.repository.owner_registration.OwnerRegistrationRepository;
import com.traveloveapi.service.redis.RedisService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController("/notification")
@AllArgsConstructor
public class NotificationController {
    final private NotificationRepository notificationRepository;
    final private OwnerRegistrationRepository ownerRegistrationRepository;
    final private ServiceRepository serviceRepository;
    final private UserService userService;
    final private RedisService redisService;

    @GetMapping("/owner")
    @Tag(name = "Notification")
    public List<NotificationEntity> getNotification(@RequestParam int page, @RequestParam int page_size) {
        return notificationRepository.getAll(page, page_size, SecurityContext.getUserID());
    }

    @GetMapping("/owner/unread")
    @Tag(name = "Notification")
    public List<NotificationEntity> getUnreadNotification() {
        return notificationRepository.getUnread(SecurityContext.getUserID());
    }

    @PutMapping("/owner/read")
    @Tag(name = "Notification")
    public void readNotification(@RequestParam Timestamp latest_time) {
        notificationRepository.readNotification(latest_time, SecurityContext.getUserID());
    }

    @GetMapping("/admin/owner-registration")
    @Tag(name = "Notification")
    public List<TourOwnerRegistrationEntity> getUnsolvedTourOwnerRegistration() {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return ownerRegistrationRepository.getByStatus(OwnerRegistrationStatus.PENDING);
    }

    @GetMapping("/admin/tour-pending")
    @Tag(name = "Notification")
    public List<ServiceEntity> getPendingTour() {
        if (!userService.isAdmin())
            throw new ForbiddenException();
        return serviceRepository.findByStatus(ServiceStatus.PENDING);
    }
}
