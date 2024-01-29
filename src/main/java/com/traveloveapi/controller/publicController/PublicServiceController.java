package com.traveloveapi.controller.publicController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveloveapi.DTO.service.RequestCheckAvailablePackageDTO;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.DTO.service.ServiceStatusByDateDTO;
import com.traveloveapi.DTO.service_package.GroupOptionDTO;
import com.traveloveapi.DTO.service_package.PackageInfoDTO;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.searching.ServiceSearchingRepository;
import com.traveloveapi.service.BillService;
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
    final private ServiceSearchingRepository serviceSearchingRepository;
    final private ActivityLoggingService activityLoggingService;
    @GetMapping("/tour")
    @Tag(name = "SPRINT 2")
    public ServiceDetailDTO getTour(@RequestParam String id) {

        ServiceDetailDTO tour = tourService.getTour(id);;
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
    public ArrayList<ServiceStatusByDateDTO> checkServiceStatusByMonth(@RequestBody RequestCheckAvailablePackageDTO request) throws JsonProcessingException {
        //RequestCheckAvailablePackageDTO request = new ObjectMapper().readValue(data, RequestCheckAvailablePackageDTO.class);
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


//        for (int i=15; i<=15; i++)
//        {
//            ServiceStatusByDateDTO status = new ServiceStatusByDateDTO();
//            status.setRemain(billService.getAvailablePackage(service_id, new Date(year-1900, month-1, i), options));
//            status.setAvailable(status.getRemain() != 0);
//            status.setDate(new Date(year-1900, month-1, i));
//            status.setCause("");
//            result.add(status);
//        }
        return result;
    }

    @Tag(name = "SPRINT 4: User side")
    @GetMapping("/search")
    public List search(@RequestParam String title) {
        return serviceSearchingRepository.findByTitle(title, 0, 5);
    }
}