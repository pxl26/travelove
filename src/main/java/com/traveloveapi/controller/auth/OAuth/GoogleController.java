package com.traveloveapi.controller.auth.OAuth;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.service.OAuth.GoogleService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/auth/oauth/google")
@RequiredArgsConstructor
public class GoogleController {
    private String client_id="226555921335-sg94jf64gf5lq0rdfbo4ulr28i2u8fqu.apps.googleusercontent.com";
    private String scope="https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile";
    private String redirect_uri="http%3A%2F%2Flocalhost%3A8080%2Fauth%2Foauth%2Fgoogle";
    private final GoogleService googleService;

    @GetMapping("/getlink")
    public SimpleResponse googleGetLink() {
        System.out.println("hello em iu");
        String link = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri="+redirect_uri +"&prompt=consent&response_type=code&client_id=" + client_id +"&scope=" + scope + "&access_type=offline";
        return new SimpleResponse(link, 200);
    }

    @GetMapping
    public TokenResponse login(@RequestParam String code) throws IOException {
        System.out.println("Code la: " + code);
        return googleService.login(code);
    }
}
