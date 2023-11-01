package com.traveloveapi.controller.auth.login;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.login.EmailLoginRequest;
import com.traveloveapi.DTO.login.UsernameLoginRequest;
import com.traveloveapi.service.login.LoginService;
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

    @GetMapping("/username")
    public TokenResponse usernameLogin(@RequestBody UsernameLoginRequest request) {
        return loginService.usernameLogin(request.getUsername(),request.getPassword());
    }
}
