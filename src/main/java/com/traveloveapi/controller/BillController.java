package com.traveloveapi.controller;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.payment.PaymentService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    final private BillService billService;
    final private PaymentService paymentService;
    @PostMapping
    @Tags({
            @Tag(name = "SPRINT 2"),
            @Tag(name = "SPRINT 5")})
    public BillDTO createBill(@RequestBody BillRequest request) {
        return billService.createNewBill(request);
    }

    @GetMapping("/payment-gateway")
    @Tag(name = "SPRINT 5")
    public GatewayResponse getPaymentGateway(@RequestParam String order_id, @RequestParam PayMethod method) {
        return paymentService.getPaymentGateway(order_id, "", method);
    }

    @PostMapping("/update")
    @Operation(hidden = true)
    public SimpleResponse updateStatus(@RequestParam String order_id, @RequestParam String bank_code, @RequestParam String status_code,@RequestParam PayMethod method) {
        if ((status_code.equals("00")&&method==PayMethod.VNPAY) || (status_code.equals("1")&&method==PayMethod.ZALOPAY)) {
            paymentService.updateBillWasPaid(order_id);
        }
        return new SimpleResponse("Hello payment", 200);
    }

    @GetMapping
    @Tag(name = "SPRINT 5")
    public ArrayList<BillEntity> getBill(@RequestParam(required = false) String bill_id, @RequestParam(required = false) String tour_id, @RequestParam(required = false) Date date) {
        if (bill_id!=null) {
            BillEntity temp = paymentService.getBill(bill_id);
            if (temp==null)
                return new ArrayList<>();
            ArrayList<BillEntity> rs = new ArrayList<>();
            rs.add(temp);
            return rs;
        }
        if (tour_id==null && date!=null)
            throw new CustomException("tour_id and date must be a pair", 400);
        if (tour_id==null)
            return paymentService.getBillByUser(SecurityContext.getUserID());
        return paymentService.getBillByTour(tour_id, date);
    }
}
