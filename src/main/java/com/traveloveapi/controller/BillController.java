package com.traveloveapi.controller;

import com.traveloveapi.DTO.BillCurrencyDTO;
import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.DTO.service_package.BillDTO;
import com.traveloveapi.DTO.service_package.BillRequest;
import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.ServiceDetailRepository;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.BillService;
import com.traveloveapi.service.currency.CurrencyService;
import com.traveloveapi.service.payment.PaymentService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    final private RabbitTemplate rabbitTemplate;
    final private ServiceDetailRepository serviceDetailRepository;
    final private CurrencyService currencyService;

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
            rabbitTemplate.convertAndSend("booking", order_id);
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

    public List<BillCurrencyDTO> getBill(@RequestParam(required = false) String tour_id, @RequestParam(required = false) Date from, @RequestParam(required = false) Date to, @RequestParam(required = false) String owner_id, @RequestParam(required = false) String currency) {
        UserEntity user = userService.getUser(SecurityContext.getUserID());

        // FOR USER
        if (tour_id==null && user.getRole()== Role.USER)
            return convert(paymentService.getBillByUser(SecurityContext.getUserID()), currency);
        //
        if (user.getRole() == Role.USER)
            throw new ForbiddenException();
        // FOR ADMIN
        if (owner_id!=null)
        {
            if (user.getRole()!= Role.ADMIN)
                throw new ForbiddenException();
            return convert(billRepository.findByOwner(owner_id, from, to), currency);
        }
        // FOR OWNER
        if (tour_id!=null)
            return convert(paymentService.getBillByTour(tour_id, from, to), currency);
        else
            return convert(billRepository.findByOwner(SecurityContext.getUserID(), from, to), currency);
    }

    private List<BillCurrencyDTO> convert(List<BillEntity> list, String currency) {
        ModelMapper modelMapper = new ModelMapper();
        return list.stream().map(bill -> {
            ServiceDetailEntity tour = serviceDetailRepository.find(bill.getService_id());
            BillCurrencyDTO dto = new BillCurrencyDTO();
            dto = modelMapper.map(bill, BillCurrencyDTO.class);
            dto.setUserCurrency(currency);
            dto.setOriginCurrency(tour.getCurrency());
            if (currency!=null)
                dto.setTotal(currencyService.convert(dto.getOriginCurrency(), dto.getUserCurrency(), dto.getTotal()));
            return dto;
        }).toList();
    }
    

    @GetMapping("/detail")
    @Tag(name = "SPRINT 8")
    public BillDetailDTO test(@RequestParam String bill_id) {
        return billService.getBillDetail(bill_id);
    }

}
