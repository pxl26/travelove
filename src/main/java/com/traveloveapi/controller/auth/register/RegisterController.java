package com.traveloveapi.controller.auth.register;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.registration.EmailRegistrationRequest;
import com.traveloveapi.DTO.registration.UsernameRegistrationRequest;
import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.exception.ExpiredCodeException;
import com.traveloveapi.exception.IncorrectCodeException;
import com.traveloveapi.repository.OtpRepository;
import com.traveloveapi.service.email.MailService;
import com.traveloveapi.service.register.RegisterService;
import com.traveloveapi.utility.OTPCodeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    private final OtpRepository otpRepository;
    private final MailService mailService;
    private final RegisterService registerService;


    @PostMapping("/email/send-code")
    public SimpleResponse byEmail(@RequestBody EmailRegistrationRequest request) {
        long expiredTime = 180000L;

        String code = OTPCodeProvider.GenegateOTP(5);
        String id = UUID.randomUUID().toString();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setId(id);
        otpEntity.setType("REGISTER-EMAIL");
        otpEntity.setUser_id("");
        otpEntity.setCode(code);
        otpEntity.setAddress(request.getEmail());
        otpEntity.setNote(request.getPassword());
        otpEntity.setExpiration(new Timestamp(System.currentTimeMillis()+expiredTime));
        otpRepository.save(otpEntity);
        mailService.sendEmail(request.getEmail(), code);
        return new SimpleResponse(id, 200);
    }

    @PostMapping("/email/verify-code")
    public TokenResponse vertify(@RequestParam String id, @RequestParam String code) {
        OtpEntity otpEntity = otpRepository.find(id);
        if (!code.equals(otpEntity.getCode()))
            throw new IncorrectCodeException();
        if (otpEntity.getExpiration().getTime() < System.currentTimeMillis())
            throw new ExpiredCodeException();
        return registerService.byEmailPassword(otpEntity.getAddress(), otpEntity.getNote());
    }

    //-----------------------------------------
    @PostMapping("/admin")
    public TokenResponse adminRegister(@RequestBody UsernameRegistrationRequest request) {
        return registerService.usernameRegister(request.getUsername(), request.getPassword(), request.getRegistration_key());
    }
}
