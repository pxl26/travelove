package com.traveloveapi.controller.publicController;

import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private BillRepository billRepository;
    final private BillService billService;

}
