package com.traveloveapi.service.logging;

import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.logging.ActivityLogEntity;
import com.traveloveapi.repository.ActivityLogRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.service.tour.TourService;
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
    final private ServiceRepository serviceRepository;

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

    public ArrayList<ViewedTourDTO> getViewedTour(String user_id, int page) {
        int page_size = 7;
        ArrayList<ActivityLogEntity> logs =  activityLogRepository.get(user_id, page_size*page, page_size);
        ArrayList<ViewedTourDTO> rs = new ArrayList<>();
        for (ActivityLogEntity ele: logs) {
            ViewedTourDTO dto = new ViewedTourDTO();
            ServiceEntity service = serviceRepository.find(ele.getRef_action());
            dto.setTour_id(ele.getRef_action());
            dto.setTitle(service.getTitle());
            dto.setMin_price(service.getMin_price());
            dto.setThumb(service.getThumbnail());
            rs.add(dto);
        }
        return rs;
    }
}
