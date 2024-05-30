package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.HealthCheckResponse;
import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.entity.CurrencyEntity;
import com.traveloveapi.repository.CurrencyConversionRepository;
import com.traveloveapi.service.redis.RedisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public/test")
public class TestController {
    final private RabbitTemplate rabbitTemplate;
    final private CurrencyConversionRepository currencyRepository;
    final private RedisService redisService;


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
        List<String> supportedList = Arrays.asList(new String[]{"XAG", "XAU", "USDC", "USDT", "PLN", "UGX", "GGP", "MWK", "NAD", "ALL", "BHD", "JEP", "BWP", "MRU", "BMD", "MNT", "FKP", "PYG", "AUD", "KYD", "RWF", "WST", "SHP", "SOS", "SSP", "BIF", "SEK", "CUC", "BTN", "MOP", "XDR", "IMP", "INR", "BYN", "BOB", "SRD", "GEL", "ZWL", "EUR", "BBD", "RSD", "SDG", "VND", "VES", "ZMW", "KGS", "HUF", "BND", "BAM", "CVE", "BGN", "NOK", "BRL", "JPY", "HRK", "HKD", "ISK", "IDR", "KRW", "KHR", "XAF", "CHF", "MXN", "PHP", "RON", "RUB", "SGD", "AED", "KWD", "CAD", "PKR", "CLP", "CNY", "COP", "AOA", "KMF", "CRC", "CUP", "GNF", "NZD", "EGP", "DJF", "ANG", "DOP", "JOD", "AZN", "SVC", "NGN", "ERN", "SZL", "DKK", "ETB", "FJD", "XPF", "GMD", "AFN", "GHS", "GIP", "GTQ", "HNL", "GYD", "HTG", "XCD", "GBP", "AMD", "IRR", "JMD", "IQD", "KZT", "KES", "ILS", "LYD", "LSL", "LBP", "LRD", "AWG", "MKD", "LAK", "MGA", "ZAR", "MDL", "MVR", "MUR", "MMK", "MAD", "XOF", "MZN", "MYR", "OMR", "NIO", "NPR", "PAB", "PGK", "PEN", "ARS", "SAR", "QAR", "SCR", "SLL", "LKR", "SBD", "VUV", "USD", "DZD", "BDT", "BSD", "BZD", "CDF", "UAH", "YER", "TMT", "UZS", "UYU", "CZK", "SYP", "TJS", "TWD", "TZS", "TOP", "TTD", "THB", "TRY", "TND"});
        StringBuilder list_query = new StringBuilder("STN");
        for (String cur : supportedList)
            list_query.append(',').append(cur);
        List<CurrencyEntity> currencyList = currencyRepository.getAll();
        HttpResponse<String> response = null;
        String dataURL = "https://api.getgeoapi.com/v2/currency/convert?api_key=620991ba804eece81325b7592470171aa3a0b58d&from=USD&to=" + list_query.toString();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataURL)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
        for (String cur : supportedList) {
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String data = response.body();
                JSONObject obj = new JSONObject(data);
                String currency = obj.getJSONObject("rates").getJSONObject(cur).getString("rate");
                CurrencyEntity newCur = new CurrencyEntity(cur, Double.parseDouble(currency));
                if (currencyRepository.getCurrencyConversion(cur) == null)
                    currencyRepository.save(newCur);
                else
                    currencyRepository.update(newCur);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        //-------DELETE CURRENCY RECORD ON REDIS
        Set<String> keys = redisService.getConnection().keys("currency:*");
        for (String key : keys) {
            redisService.getConnection().del(key);
        }
    }
}
