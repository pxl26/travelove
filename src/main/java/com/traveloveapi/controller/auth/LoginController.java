package com.traveloveapi.controller.auth;

import com.traveloveapi.security.JwtProvider;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@Data
public class LoginController {
    @GetMapping(value = "/test")
    public String login(@RequestParam String name){
        JwtProvider jwt = new JwtProvider();
        return jwt.generateToken(name, 86400000L);
    }
}