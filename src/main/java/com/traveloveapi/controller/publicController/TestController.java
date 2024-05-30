package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.HealthCheckResponse;
import com.traveloveapi.DTO.activity.ViewedTourDTO;
import com.traveloveapi.repository.CurrencyConversionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


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
}
