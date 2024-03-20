package com.traveloveapi.service.login;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.IncorrectPasswordException;
import com.traveloveapi.exception.UserNotFoundException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.JwtProvider;
import jakarta.persistence.NoResultException;
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
        return login(password, detail);
    }


    public TokenResponse usernameLogin(String username, String password) {
        UserDetailEntity detail = userDetailRepository.findByUsername(username);
        return login(password, detail);
    }
    //|------------------------------------------------------------------------|
    //|               GENERAL LOGIN STEP                                       |
    //|------------------------------------------------------------------------|
    private TokenResponse login(String password, UserDetailEntity detail) {
        if (detail==null)
            throw new UserNotFoundException();
        if (!passwordEncoder.matches(password, detail.getPassword())) {
            throw new IncorrectPasswordException();
        }
        UserEntity user = userRepository.find(detail.getUser_id());
        return JwtProvider.generateTokenResponse(detail.getUser_id(), user.getRole());
    }
}
