package com.traveloveapi.controller.auth.register;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.registration.EmailRegistrationRequest;
import com.traveloveapi.DTO.registration.UsernameRegistrationRequest;
import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.ExpiredCodeException;
import com.traveloveapi.exception.IncorrectCodeException;
import com.traveloveapi.repository.OtpRepository;
import com.traveloveapi.service.email.MailService;
import com.traveloveapi.service.register.RegisterService;
import com.traveloveapi.utility.OTPCodeProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    private final OtpRepository otpRepository;
    private final MailService mailService;
    private final RegisterService registerService;


    @Tag(name = "SPRINT 1")
    @PostMapping("/email/send-code")
    public SimpleResponse byEmail(@RequestBody EmailRegistrationRequest request) {
        String code = OTPCodeProvider.GenegateOTP(5);
        String id = UUID.randomUUID().toString();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setId(id);
        otpEntity.setType("REGISTER-EMAIL");
        otpEntity.setUser_id("");
        otpEntity.setCode(code);
        otpEntity.setAddress(request.getEmail());
        otpEntity.setNote(request.getPassword());
        otpEntity.setExpiration(new Timestamp(System.currentTimeMillis()+180000L));  // 3'
        otpRepository.save(otpEntity);
        mailService.sendEmail(request.getEmail(), code);
        return new SimpleResponse(id, 200);
    }

    @Tag(name = "SPRINT 1")
    @PostMapping("/email/verify-code")
    public TokenResponse vertify(@RequestParam String id, @RequestParam String code) {
        OtpEntity otpEntity = otpRepository.find(id);
        if (otpEntity==null)
            throw new CustomException("Id not found", 400);
        if (!code.equals(otpEntity.getCode()))
            throw new IncorrectCodeException();
        if (otpEntity.getExpiration().getTime() < System.currentTimeMillis())
            throw new ExpiredCodeException();
        return registerService.byEmailPassword(otpEntity.getAddress(), otpEntity.getNote());
    }

    //-----------------------------------------
    @Operation(hidden = true)
    @PostMapping("/username")
    public TokenResponse adminRegister(@RequestBody UsernameRegistrationRequest request) {
        return registerService.usernameRegister(request.getUsername(), request.getPassword(), request.getRegistration_key());
    }
}
