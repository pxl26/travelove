package com.traveloveapi.controller.publicController;

import com.traveloveapi.entity.join_entity.JoinBillDetail;
import com.traveloveapi.repository.service_package.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private BillRepository billRepository;
    @GetMapping
    public List test(@RequestParam String id) {
        return billRepository.getBillDetail(id);
    }
}
