package com.traveloveapi.controller.auth;

import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.repository.OtpRepository;
import com.traveloveapi.service.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    private final OtpRepository otpRepository;
    private final MailService mailService;
    @PostMapping("/email")
    public String byEmail(@RequestParam String email) {
        Random rand = new Random();
        int random_num = rand.nextInt(10);
        int code = random_num*10000 + rand.nextInt(10000);
        String id = UUID.randomUUID().toString();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setId(id);
        otpEntity.setType("REGISTER-EMAIL");
        otpEntity.setUser_id("");
        otpEntity.setCode(String.valueOf(code));
        otpEntity.setExpiration(new Date(System.currentTimeMillis()));
        otpRepository.save(otpEntity);
        mailService.sendEmail(email, String.valueOf(code));
        return id;
    }

    @PostMapping("/verify")
    public String vertify(@RequestParam String id, @RequestParam String code) {
        OtpEntity otpEntity = otpRepository.find(id);
        if (code.equals(otpEntity.getCode()))
            return "Successfully";
        else return "Incorrect";
    }
}
