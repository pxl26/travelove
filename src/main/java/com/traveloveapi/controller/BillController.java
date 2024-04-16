package com.traveloveapi.controller;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.payment.PaymentService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    final private BillService billService;
    final private PaymentService paymentService;
    final private UserService userService;
    final private BillRepository billRepository;

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
        else
            paymentService.updateBillWasCancelled(order_id);
        return new SimpleResponse("Oke payment", 200);
    }

    @GetMapping
    @Tags({
            @Tag(name = "SPRINT 5", description = "It's serve for all role"),
            @Tag(name = "MANAGE", description = "It's serve for all role")
    })

    public ArrayList<BillEntity> getBill(@RequestParam(required = false) String tour_id, @RequestParam(required = false) Date from, @RequestParam(required = false) Date to, @RequestParam(required = false) String status_code, @RequestParam(required = false) String owner_id) {
        UserEntity user = userService.getUser(SecurityContext.getUserID());

        // FOR USER
        if (tour_id==null && user.getRole()== Role.USER)
            return paymentService.getBillByUser(SecurityContext.getUserID());
        //
        if (user.getRole() == Role.USER)
            throw new ForbiddenException();
        // FOR ADMIN
        if (owner_id!=null)
        {
            if (user.getRole()!= Role.ADMIN)
                throw new ForbiddenException();
            return (ArrayList<BillEntity>) billRepository.findByOwner(owner_id, from, to);
        }
        // FOR OWNER
        if (tour_id!=null)
            return paymentService.getBillByTour(tour_id, from, to);
        else
            return (ArrayList<BillEntity>) billRepository.findByOwner(SecurityContext.getUserID(), from, to);
    }
    

    @GetMapping("/detail")
    @Tag(name = "SPRINT 8")
    public BillDetailDTO test(@RequestParam String bill_id) {
        return billService.getBillDetail(bill_id);
    }

}
