package com.traveloveapi.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class PaymentService {
    final private BillRepository billRepository;
    final private UserService userService;
    final private ServiceRepository serviceRepository;

    @Value("${payment.vnpay}")
    private String vnpay_endpoint;

    @Value("${payment.zalopay}")
    private String zalopay_endpoint;

    @Value("${payment.paypal}")
    private String paypal_endpoint;

    public GatewayResponse getPaymentGateway(String bill_id, String bank_code, PayMethod method) {
        BillEntity bill = billRepository.find(bill_id);
        if (bill==null)
            throw new CustomException("Bill not found", 404);
        if (bill.getStatus()==BillStatus.PAID)
            throw new CustomException("Bill was paid", 400);
        if (bill.getStatus()==BillStatus.CANCELED)
            throw new CustomException("Bill was canceled", 400);
        if (bill.getCurrency().equals("VND") && method==PayMethod.PAYPAL)
            throw new CustomException("VND is not supported by Paypal", 400);
        if (!bill.getCurrency().equals("VND") && method!=PayMethod.PAYPAL)
            throw new CustomException("VND is required", 400);
        String amount = String.valueOf(Math.round(bill.getTotal()));
        String order_description = "Thanh%20toán%20cho%20tour:%20" + bill.getService_id();
        String order_type = "Thanhtoan";
        String request_url = (method==PayMethod.VNPAY ? vnpay_endpoint : ( method==PayMethod.ZALOPAY ? zalopay_endpoint : paypal_endpoint)) + "?bank_code="+bank_code+"&amount="+(method==PayMethod.PAYPAL ? bill.getTotal() : amount)+"&order_description="+order_description+"&order_type="+order_type + "&order_id="+bill_id + "&currency=" + bill.getCurrency();
        System.out.println(request_url);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(request_url)).method("POST",HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CustomException("Cannot connect to payment server", 500);
        }
        assert response != null;
        String data = response.body();
        System.out.println(data);

        GatewayResponse gateway_data = new GatewayResponse();
        try {
            gateway_data = new ObjectMapper().readValue(data, GatewayResponse.class);
        }
        catch (Exception ex) {
            throw new CustomException("Cannot map data from payment server", 500);
        }
        if (gateway_data.getUrl()==null)
            throw new CustomException("Duplicated order id", 400);
        bill.setGateway_url(gateway_data.getUrl());
        bill.setPay_method(method);
        billRepository.update(bill);
        return gateway_data;
    }

    public void updateBillWasPaid(String bill_id){
        BillEntity bill = billRepository.find(bill_id);
        bill.setStatus(BillStatus.PAID);
        bill.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        billRepository.update(bill);
        ServiceEntity service = serviceRepository.findAdmin(bill.getService_id());
        service.setSold(service.getSold()+1);
        serviceRepository.update(service);
    }

    public void updateBillWasCancelled(String bill_id){
        BillEntity bill = billRepository.find(bill_id);
        bill.setStatus(BillStatus.CANCELED);
        bill.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        billRepository.update(bill);
    }


    public ArrayList<BillEntity> getBillByUser(String user_id) {
        if (!SecurityContext.getUserID().equals(user_id) && !userService.isAdmin())
            throw new ForbiddenException();
        return billRepository.findByUser(user_id);
    }

    public ArrayList<BillEntity> getBillByTour(String tour_id, Date from, Date to) {
        if (!userService.isAdmin() && !userService.verifyIsOwner(tour_id, SecurityContext.getUserID()))
            throw new ForbiddenException();
        if (from!=null&to!=null)
            return billRepository.findByService(tour_id, from, to);
        return billRepository.findByService(tour_id);
    }
}
