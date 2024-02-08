package com.traveloveapi.service.logging;

import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.constrain.UserAction;
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
        if (SecurityContext.getUserID().equals("anonymousUser"))   //view tour by anonymous
            return;
        System.out.println("FAIL");
        ActivityLogEntity entity = new ActivityLogEntity();
        entity.setUser_id(SecurityContext.getUserID());
        entity.setId(UUID.randomUUID().toString());
        entity.setRef_action(tour_id);
        entity.setTime(new Date());
        entity.setAction(UserAction.VIEW_TOUR);
        activityLogRepository.save(entity);
    }

    public ArrayList<ActivityLogEntity> getMyActivityLog(int page) {
        //1 page = 7 record
        int page_size = 7;
        return activityLogRepository.get(SecurityContext.getUserID(), page_size*page, page_size);
    }

    public ArrayList<ViewedTourDTO> getViewedTour(String user_id) {
        int page_size = 10;
        ArrayList<ViewedTourDTO> rs = new ArrayList<>();
        int page = 0;

        while (true) {
            ArrayList<ActivityLogEntity> logs = activityLogRepository.get(user_id, page_size * page, page_size, UserAction.VIEW_TOUR);
            if (logs.isEmpty())
                return rs;
            for (ActivityLogEntity ele : logs) {
                boolean isDupicate = false;
                for (ViewedTourDTO tour : rs)
                    if (ele.getRef_action().equals(tour.getTour_id())) {
                        isDupicate = true;
                        break;
                    }
                if (isDupicate)
                    continue;
                ViewedTourDTO dto = new ViewedTourDTO();
                ServiceEntity service = serviceRepository.find(ele.getRef_action());
                dto.setTour_id(ele.getRef_action());
                dto.setTitle(service.getTitle());
                dto.setMin_price(service.getMin_price());
                dto.setThumb(service.getThumbnail());
                rs.add(dto);
            }
            if (rs.size()>=7)
                return rs;
            page++;
        }
    }
}
