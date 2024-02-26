package com.traveloveapi.controller.publicController;

import com.traveloveapi.repository.service_package.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private BillRepository billRepository;
    @GetMapping
    public void test(@RequestParam String id) {
        billRepository.getBillDetail(id);
    }
}
