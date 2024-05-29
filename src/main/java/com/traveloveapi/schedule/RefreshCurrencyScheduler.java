package com.traveloveapi.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.DTO.currencyConvert.CurrencyConvertResponse;
import com.traveloveapi.entity.CurrencyEntity;
import com.traveloveapi.repository.CurrencyConversionRepository;
import com.traveloveapi.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RefreshCurrencyScheduler {
    private final CurrencyConversionRepository currencyRepository;
    private final RedisService redisService;

//    @Scheduled(cron = "0 7 * * * ")
//    public void refreshCurrencyConvertingData() {
//        List<CurrencyEntity> currencyList = currencyRepository.getAll();
//        for (CurrencyEntity currencyEntity : currencyList) {
//            HttpResponse<String> response = null;
//            String dataURL = "https://api.getgeoapi.com/v2/currency/convert?api_key=620991ba804eece81325b7592470171aa3a0b58d&from=USD&to=" + currencyEntity.getCurrency();
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataURL)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
//            try {
//                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//                String data = response.body();
//                CurrencyConvertResponse resData = new ObjectMapper().readValue(data, CurrencyConvertResponse.class);
//
//                currencyEntity.setAmount(Double.valueOf(resData.getRates().getGBP().getRate()));
//                currencyRepository.update(currencyEntity);
//            } catch (IOException | InterruptedException e) {
//                System.out.println("loi r");
//            }
//        }
//    }
}
