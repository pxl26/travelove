package com.traveloveapi.controller.publicController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.feedback.FeedbackDTO;
import com.traveloveapi.DTO.service.RequestCheckAvailablePackageDTO;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.DTO.service.ServiceStatusByDateDTO;
import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import com.traveloveapi.DTO.service_package.PackageInfoDTO;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.service.redis.RedisService;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.feedback.FeedbackService;
import com.traveloveapi.service.logging.ActivityLoggingService;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/service")
public class PublicServiceController {
    final private TourService tourService;
    final private BillService billService;
    final private FeedbackService feedbackService;
    final private ActivityLoggingService activityLoggingService;
    final private ServiceRepository serviceRepository;

    @GetMapping("/get-all-tour")
    @Tag(name = "AI support")
    public List<ServiceDetailDTO> getAll() {
        List<ServiceEntity> data =  serviceRepository.getAll();
        ArrayList<ServiceDetailDTO> serviceDetailDTOs = new ArrayList<>();
        for (ServiceEntity serviceEntity : data) {
            serviceDetailDTOs.add(tourService.getTour(serviceEntity.getId()));
        }
        return serviceDetailDTOs;
    }

    @GetMapping("/tour")
    @Tag(name = "SPRINT 2")
    public ServiceDetailDTO getTour(@RequestParam String id)  {
        ServiceDetailDTO tour = tourService.getTour(id);
        activityLoggingService.viewTour(tour.getId());
        return tour;
    }


    @GetMapping("/package")
    @Tag(name = "SPRINT 2")
    public PackageInfoDTO getPackage(@RequestParam String service_id) {
        return tourService.getPackageInfo(service_id);
    }

    @PutMapping("/check-available")
    @Tag(name="SPRINT 2")
    //RequestCheckAvailablePackageDTO
    public ArrayList<ServiceStatusByDateDTO> checkServiceStatusByMonth(@RequestBody RequestCheckAvailablePackageDTO request) {
        int month = request.getMonth();
        int year = request.getYear();
        String service_id = request.getService_id();
        ArrayList<GroupOptionDTO> options = request.getOption_list();

        Calendar now  = Calendar.getInstance();
        if (now.get(Calendar.YEAR) > year)
            throw new CustomException("Invalid year", 400);
        if (now.get(Calendar.MONTH) + 1 < month)
            throw  new CustomException("Invalid month", 400);

        int lengthOfMonth = YearMonth.now().lengthOfMonth();
        ArrayList<ServiceStatusByDateDTO> result = new ArrayList<>();

        int start_day = now.get(Calendar.MONTH)==month ? now.get(Calendar.DAY_OF_MONTH) : 1;
        for (int i=start_day; i<=lengthOfMonth; i++)
        {
            ServiceStatusByDateDTO status = new ServiceStatusByDateDTO();
            status.setRemain(billService.getAvailablePackage(service_id, new Date(year-1900, month-1, i), options));
            status.setAvailable(status.getRemain() != 0);
            status.setDate(new Date(year-1900, month-1, i));
            status.setCause("");
            result.add(status);
        }
        return result;
    }


    @GetMapping("/feedback")
    @Tag(name = "SPRINT 8")
    public ArrayList<FeedbackDTO> getFeedback(@RequestParam String tour_id, @RequestParam int rating_from, @RequestParam int rating_to, @RequestParam int page) {
        return feedbackService.getByTour(tour_id, rating_from, rating_to, page);
    }

    @GetMapping("/feedback-has-media")
    @Tag(name = "SPRINT 8")
    public ArrayList<FeedbackDTO> getFeedbackHasMedia(@RequestParam String tour_id, @RequestParam int page) {
        return feedbackService.getFeedbackHasMedia(tour_id, page);
    }

    @GetMapping("/feedback/get-one")
    @Tag(name = "SPRINT 8")
    public FeedbackDTO getFeedbackById(@RequestParam String feedback_id) {
        return feedbackService.getFeedback(feedback_id);
    }

    @GetMapping("/random")
    @Tag(name = "CARD")
    public List getRandomTour(@RequestParam int quantity) {
        return serviceRepository.getRandomTour(quantity);
    }

    @GetMapping("/best-seller")
    public List getBestSeller(@RequestParam int quantity) {
        List<ServiceEntity> tour = serviceRepository.getBestSellers(quantity);
        ArrayList<ServiceDetailDTO> serviceDetailDTOs = new ArrayList<>();
        for (ServiceEntity serviceEntity : tour) {
            serviceDetailDTOs.add(tourService.getTour(serviceEntity.getId()));
        }
        return serviceDetailDTOs;
    }
}