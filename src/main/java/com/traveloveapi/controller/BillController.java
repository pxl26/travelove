package com.traveloveapi.controller;

import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.service.BillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    final private BillService billService;
    @PostMapping
    @Tag(name = "SPRINT 2")
    public BillDTO createBill(@RequestBody BillRequest request) {
        return billService.createNewBill(request);
    }
}
