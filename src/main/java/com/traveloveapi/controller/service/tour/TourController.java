package com.traveloveapi.controller.service.tour;

import com.traveloveapi.DTO.service.CreateTourDTO;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.DTO.service.ServiceStatusByDateDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.constrain.ServiceType;
import com.traveloveapi.service.tour.TourService;
import com.traveloveapi.service.user.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class TourController {
    final private TourService tourService;
    @PostMapping("/new")
    @Tag(name = "SPRINT 2")
    public ServiceDetailDTO createService(@RequestParam ServiceType service_type, @RequestParam String title, @RequestParam String description, @RequestParam String highlight, @RequestParam String note, @RequestParam MultipartFile[] files, @RequestParam Currency currency, @RequestParam Language primary_language, @RequestParam Float min_price) throws IOException, InterruptedException {
        return tourService.createNewService(service_type, title, description, highlight, note,currency,primary_language, files, min_price);
    }

    @GetMapping("/pending")
    @Tags({
            @Tag(name = "SPRINT 2"),
            @Tag(name = "SPRINT 2")
    })
    public ArrayList<ServiceDetailDTO> getPendingService(@RequestParam(required = false) String owner) {
        if (owner==null)
            owner="";
        return tourService.getPendingTour(owner);
    }

    @PutMapping
    @Tag(name = "SPRINT 4: Service owner side")
    public ServiceDetailDTO editService(@RequestParam String service_id,@RequestParam(required = false) ServiceType service_type, @RequestParam(required = false) String title, @RequestParam(required = false) String description, @RequestParam(required = false) String highlight, @RequestParam(required = false) String note, @RequestParam(required = false) Currency currency, @RequestParam(required = false) Language primary_language, @RequestParam(required = false) float min_price) {
        return tourService.editTour(service_id, service_type, title, description,highlight,note, currency, primary_language, min_price);
    }
}
