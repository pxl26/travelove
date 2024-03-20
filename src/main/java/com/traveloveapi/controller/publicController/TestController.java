package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {

    @Tag(name = "Load testing")
    @GetMapping("/empty-get")
    public String emptyGet() {
        return "Ok";
    }

    @Tag(name = "Load testing")
    @PostMapping("/empty-post")
    public String emptyPost(@RequestBody ViewedTourDTO data) {
        return "Ok";
    }
}
