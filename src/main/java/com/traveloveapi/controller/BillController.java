package com.traveloveapi.controller;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    final private BillService billService;
    final private PaymentService paymentService;
    @PostMapping
    @Tag(name = "SPRINT 2")
    public BillDTO createBill(@RequestBody BillRequest request) {
        return billService.createNewBill(request);
    }

    @GetMapping("/payment-gateway")
    @Tag(name = "SPRINT 5: User")
    public GatewayResponse getPaymentGateway(@RequestParam String order_id, @RequestParam String bank_code) {
        return paymentService.getPaymentGateway(order_id, bank_code);
    }

    @PostMapping("/update")
    @Operation(hidden = true)
    public SimpleResponse updateStatus(@RequestParam String order_id, @RequestParam String bank_code, @RequestParam String status_code) {
        System.out.println(status_code);
        if (status_code.equals("00")) {
            paymentService.updateBillWasPaid(order_id);
        }
        return new SimpleResponse("Hello payment", 200);
    }
}
