package com.traveloveapi.controller.service.tour;

import com.traveloveapi.DTO.service.ServiceCard;
import com.traveloveapi.DTO.service.ServiceDetailDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.constrain.ServiceType;
import com.traveloveapi.entity.feedback.FeedbackEntity;
import com.traveloveapi.service.feedback.FeedbackService;
import com.traveloveapi.service.tour.TourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class TourController {
    final private TourService tourService;
    final private FeedbackService feedbackService;
    @PostMapping("/new")
    @Tag(name = "SPRINT 2")
    public ServiceDetailDTO createService(@RequestParam ServiceType service_type, @RequestParam String title, @RequestParam String description, @RequestParam String highlight, @RequestParam String note, @RequestParam MultipartFile[] files,@RequestParam String[] gallery_description, @RequestParam Currency currency, @RequestParam Language primary_language, @RequestParam String city_id, @RequestParam String location, @RequestParam String address) throws IOException, InterruptedException {
        return tourService.createNewService(service_type, title, description, highlight, note,currency,primary_language, files, gallery_description, city_id, location, address);
    }

    @GetMapping("/pending")
    @Tags({
            @Tag(name = "SPRINT 2")
    })
    public ArrayList<ServiceDetailDTO> getPendingService(@RequestParam(required = false) String owner) {
        if (owner==null)
            owner="";
        return tourService.getPendingTour(owner);
    }

    @PutMapping
    @Tag(name = "SPRINT 4")
    public ServiceDetailDTO editService(@RequestParam String service_id,@RequestParam(required = false) ServiceType service_type, @RequestParam(required = false) String title, @RequestParam(required = false) String description, @RequestParam(required = false) String highlight, @RequestParam(required = false) String note, @RequestParam(required = false) Currency currency, @RequestParam(required = false) Language primary_language, @RequestParam(required = false) float min_price, @RequestParam(required = false) String city_id) {
        return tourService.editTour(service_id, service_type, title, description,highlight,note, currency, primary_language, min_price, city_id);
    }

    @GetMapping("/card")
    @Tag(name = "SPRINT 5")
    public ServiceCard getCard(@RequestParam String tour_id) {
        return tourService.createCard(tour_id);
    }

    @GetMapping("/owner")
    @Tag(name = "SPRINT 5")
    public ArrayList<ServiceCard> getListTourByOwner(@RequestParam(required = false) String owner_id) {
        return tourService.getTourByOwner(owner_id);
    }

    @PostMapping("/feedback")
    @Tag(name = "SPRINT 8")
    public FeedbackEntity createFeedback(@RequestParam int rating,@RequestParam(required = false) String content, String bill_id,@RequestParam(required = false) MultipartFile[] files) {
        return feedbackService.createFeedback(rating, content, bill_id, files);
    }

    @DeleteMapping("/feedback")
    @Tag(name = "SPRINT 11")
    public FeedbackEntity deleteFeedback(@RequestParam String feedback_id) {
        return feedbackService.deleteFeedback(feedback_id);
    }
}
