package com.traveloveapi.service.logging;

import com.traveloveapi.entity.logging.ActivityLogEntity;
import com.traveloveapi.repository.ActivityLogRepository;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityLoggingService {
    final private ActivityLogRepository activityLogRepository;

    public void viewTour(String tour_id) {
        if (SecurityContext.getUserID().isEmpty())   //view tour by anonymous
            return;
        ActivityLogEntity entity = new ActivityLogEntity();
        entity.setUser_id(SecurityContext.getUserID());
        entity.setId(UUID.randomUUID().toString());
        entity.setRef_action(tour_id);
        entity.setTime(new Date());
        activityLogRepository.save(entity);
    }

    public ArrayList<ActivityLogEntity> getMyActivityLog(int page) {
        //1 page = 7 record
        int page_size = 7;
        return activityLogRepository.get(SecurityContext.getUserID(), page_size*page, page_size);
    }
}
