package com.traveloveapi.controller.admin;

import com.traveloveapi.DTO.user.UserDTO;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.RequestParamException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.register.RegisterService;
import com.traveloveapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
public class CreateAccount {
    final private UserService userService;
    final private RegisterService registerService;
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;
    @PostMapping
    public UserProfile createNewAccount(@RequestParam(required = false) String email, @RequestParam(required = false) String username, @RequestParam Role role, @RequestParam String password){
        userService.verifyIsAdmin();
        if (email==null && username==null)
            throw new RequestParamException();
        String id = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setRole(role);
        userRepository.save(user);
        UserDetailEntity detail = new UserDetailEntity();
        detail.setUser_id(id);
        detail.setCreate_at(new Timestamp(System.currentTimeMillis()));
        detail.setUsername(username);
        detail.setEmail(email);
        userDetailRepository.save(detail);
        return new UserProfile(user,detail);
    }
}
