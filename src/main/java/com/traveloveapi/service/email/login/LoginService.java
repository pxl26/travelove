package com.traveloveapi.service.email.login;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.IncorrectPasswordException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class LoginService {
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;
    final private PasswordEncoder passwordEncoder;

    public TokenResponse emailLogin(String email, String password) {
        UserDetailEntity detail = userDetailRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, detail.getPassword())) {
            throw new IncorrectPasswordException();
        }
        Long duration = 1800000L; //30'
        return new TokenResponse(JwtProvider.generateToken(detail.getUser_id(), duration),new Timestamp(System.currentTimeMillis()),duration);
    }
}
