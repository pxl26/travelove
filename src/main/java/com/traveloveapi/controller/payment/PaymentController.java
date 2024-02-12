package com.traveloveapi.controller.payment;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    final private PaymentService paymentService;

    @GetMapping
    public GatewayResponse getPaymentGateway(@RequestParam String order_id, @RequestParam String bank_code) {
        return paymentService.getPaymentGateway(order_id, bank_code);
    }

    @PostMapping("/update")
    public SimpleResponse getStatus(@RequestParam String order_id, @RequestParam String status) {
        SimpleResponse rs = new SimpleResponse("Oke", 200);
        System.out.println(order_id + " : " + status);
        return rs;
    }
}
