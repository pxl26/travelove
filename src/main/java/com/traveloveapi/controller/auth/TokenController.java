package com.traveloveapi.controller.auth;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.JwtProvider;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    final private UserRepository userRepository;
    @GetMapping("/refresh")
    @Tag(name = "SPRINT 1")
    public TokenResponse getNewToken() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        return JwtProvider.generateTokenResponse(user.getId(), user.getRole());
    }
}
