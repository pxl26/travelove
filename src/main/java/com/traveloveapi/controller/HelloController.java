package com.traveloveapi.controller;

import com.traveloveapi.service.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final MailService mailService;
    @PostMapping
    String test() {
        mailService.sendEmail("phanxuanloc2612@gmail.com", "hello bro 2");
        return "ok";
    }
    @GetMapping("/test")
    public String authTest() {
        return "pass";
    }
}
