package com.traveloveapi.service.email.registration;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.security.role.Roles;
import com.traveloveapi.utility.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;
    final private PasswordEncoder passwordEncoder;
    public TokenResponse byEmailPassword(String email, String password) {
        UserEntity newUser = new UserEntity();
        String user_id = UUID.randomUUID().toString();
        newUser.setId(user_id);
        newUser.setRole(Roles.USER.toString());
        userRepository.save(newUser);

        UserDetailEntity newDetails = new UserDetailEntity();
        newDetails.setUser_id(user_id);
        newDetails.setEmail(email);
        newDetails.setPassword(passwordEncoder.encode(password));
        userDetailRepository.save(newDetails);

        TokenResponse response = new TokenResponse();
        Long expiration = 1800000L; // 30'
        response.setToken(JwtProvider.generateToken(user_id, expiration));
        response.setExpiration(expiration);
        response.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return response;
    }
}
