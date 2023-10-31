package com.traveloveapi.controller.auth.login;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.login.EmailLoginRequest;
import com.traveloveapi.service.email.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class LoginController {
    final private LoginService loginService;

    @PostMapping("/email")
    public TokenResponse emailLogin(@RequestBody EmailLoginRequest request) {
        return loginService.emailLogin(request.getEmail(),request.getPassword());
    }
    @GetMapping
    public String test() {
        return "URL???????";
    }

    @GetMapping("/hello")
    public String greeting(@RequestParam String name) {
        return "Hello " + name + " <3 !!!";
    }
}
