package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.repository.service_package.BillRepository;
import com.traveloveapi.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private BillRepository billRepository;
    final private BillService billService;

}
