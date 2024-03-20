package com.traveloveapi.service.register;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.IncorrectKeyException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.utility.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${registration_key.admin}")
    private String admin_key;
    @Value("${registration_key.tour_owner}")
    private String tour_owner_key;

    public TokenResponse byEmailPassword(String email, String password) {
        UserEntity newUser = new UserEntity();
        String user_id = UUID.randomUUID().toString();
        newUser.setId(user_id);
        newUser.setRole(Role.USER);
        userRepository.save(newUser);

        UserDetailEntity newDetails = new UserDetailEntity();
        newDetails.setUser_id(user_id);
        newDetails.setEmail(email);
        newDetails.setPassword(passwordEncoder.encode(password));
        userDetailRepository.save(newDetails);


        return JwtProvider.generateTokenResponse(user_id, newUser.getRole());
    }

//  |--------------------------------------------------------------------|
//  |  Username-password auth only available for ADMIN and TOUR-OWNER    |
//  |--------------------------------------------------------------------|
    public TokenResponse usernameRegister(String username, String password, String key) {
        UserEntity newUser = new UserEntity();
        if (admin_key.equals(key))
            newUser.setRole(Role.ADMIN);
        else if (tour_owner_key.equals(key))
            newUser.setRole(Role.TOUR_OWNER);
        else
            throw new IncorrectKeyException();
        String id = UUID.randomUUID().toString();
        newUser.setId(id);

        UserDetailEntity detail = new UserDetailEntity();
        detail.setUser_id(id);
        detail.setPassword(passwordEncoder.encode(password));
        detail.setUsername(username);
        detail.setCreate_at(new Timestamp(System.currentTimeMillis()));
        userDetailRepository.save(detail);
        userRepository.save(newUser);

        return JwtProvider.generateTokenResponse(id, newUser.getRole());
    }
}
