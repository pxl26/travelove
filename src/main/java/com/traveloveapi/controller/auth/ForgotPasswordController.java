package com.traveloveapi.controller.auth;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.OtpEntity;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ExpiredCodeException;
import com.traveloveapi.exception.IncorrectCodeException;
import com.traveloveapi.exception.UserNotFoundException;
import com.traveloveapi.repository.OtpRepository;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.email.MailService;
import com.traveloveapi.utility.HTMLTemplate;
import com.traveloveapi.utility.JwtProvider;
import com.traveloveapi.utility.OTPCodeProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/forgot-password")
public class ForgotPasswordController {
    private final MailService mailService;
    private final OtpRepository otpRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @GetMapping
    @Tag(name = "SPRINT 1")
    public SimpleResponse requestChangeCode(@RequestParam String email) {
        UserDetailEntity userDetailEntity = userDetailRepository.findByEmail(email);
        if (userDetailEntity == null) {
            throw new UserNotFoundException();
        }
        UserEntity user = userRepository.find(userDetailEntity.getUser_id());
        OtpEntity otp = new OtpEntity();
        String id = UUID.randomUUID().toString();

        otp.setId(id);
        otp.setUser_id(userDetailEntity.getUser_id());
        otp.setAddress(email);
        otp.setExpiration(new Timestamp(System.currentTimeMillis() + 180000L)); // 3'
        otp.setType("RESET PASSWORD");
        otp.setCode(OTPCodeProvider.GenegateOTP(5));
        otpRepository.save(otp);

        mailService.sendResetPasswordEmail(email, HTMLTemplate.resetPassword(otp.getCode(), user.getFull_name()));
        return new SimpleResponse(otp.getId(), 200);
    }

    @PutMapping("/verify-code")
    @Tag(name = "SPRINT 1")
    public TokenResponse verifyCode(@RequestParam String id, @RequestParam String code) {
        OtpEntity otpEntity = otpRepository.find(id);
        if (!code.equals(otpEntity.getCode()))
            throw new IncorrectCodeException();
        if (otpEntity.getExpiration().getTime() < System.currentTimeMillis())
            throw new ExpiredCodeException();

        UserDetailEntity detail = userDetailRepository.findByEmail(otpEntity.getAddress());
        detail.setPassword(null);
        userDetailRepository.save(detail);
        UserEntity user = userRepository.find(detail.getUser_id());

        return JwtProvider.generateTokenResponse(user.getId(), user.getRole());
    }
}
