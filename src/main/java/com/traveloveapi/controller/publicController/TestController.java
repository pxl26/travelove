package com.traveloveapi.controller.publicController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.HealthCheckResponse;
import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.DTO.currencyConvert.CurrencyConvertResponse;
import com.traveloveapi.entity.CurrencyEntity;
import com.traveloveapi.repository.CurrencyConversionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private RabbitTemplate rabbitTemplate;
    final private CurrencyConversionRepository currencyRepository;


    @Tag(name = "Load testing")
    @GetMapping("/empty-get")
    public HealthCheckResponse emptyGet() {
        return new HealthCheckResponse("UP");
    }

    @Tag(name = "Load testing")
    @PostMapping("/empty-post")
    public String emptyPost(@RequestBody ViewedTourDTO data) {
        return "Ok";
    }

    @PostMapping("/mq")
    public String post(@RequestParam String message) {
        rabbitTemplate.convertAndSend("booking", message);
        return "Oke";
    }

    @GetMapping("/currency")
    public void refreshCurrencyConvertingData() {
        List<CurrencyEntity> currencyList = currencyRepository.getAll();
        for (CurrencyEntity currencyEntity : currencyList) {
            HttpResponse<String> response = null;
            String dataURL = "https://api.getgeoapi.com/v2/currency/convert?api_key=620991ba804eece81325b7592470171aa3a0b58d&from=USD&to=" + currencyEntity.getCurrency();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataURL)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String data = response.body();
                JSONObject obj = new JSONObject(data);
                String currency = obj.getJSONObject("rates").getJSONObject(currencyEntity.getCurrency()).getString("rate");

                currencyEntity.setAmount(Double.valueOf(currency));
                currencyRepository.update(currencyEntity);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
