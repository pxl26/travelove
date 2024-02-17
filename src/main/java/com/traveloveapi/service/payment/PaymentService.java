package com.traveloveapi.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.oauth.google.GoogleTokenResponse;
import com.traveloveapi.DTO.payment.GatewayResponse;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.service_package.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class PaymentService {
    final private BillRepository billRepository;

    @Value("${payment.host}")
    private String payment_service_host;
    public GatewayResponse getPaymentGateway(String bill_id, String bank_code) {
        BillEntity bill = billRepository.find(bill_id);
        if (bill==null)
            throw new CustomException("Bill not found", 404);
        if (bill.getStatus()==BillStatus.PAID)
            throw new CustomException("Bill was paid", 400);
        if (bill.getStatus()==BillStatus.CANCELED)
            throw new CustomException("Bill was canceled", 400);
        int amount = (int) bill.getTotal();
        String order_description = "Thanhtoanhoadonchotour" + bill.getService_id();
        String order_type = "Thanhtoan";
        String request_url = payment_service_host + "?bank_code="+bank_code+"&amount="+amount+"&order_description="+order_description+"&order_type="+order_type + "&order_id="+bill_id;
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
        return gateway_data;
    }

    public void updateBillWasPaid(String bill_id){
        BillEntity bill = billRepository.find(bill_id);
        bill.setStatus(BillStatus.PAID);
        bill.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        billRepository.save(bill);
    }

    public BillEntity getBill(String bill_id) {
        return billRepository.find(bill_id);
    }
}
