package com.traveloveapi.controller.user;

import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.RequestParamException;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.user.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileUserController {
    final private UserProfileService userProfileService;

    final private UserRepository userRepository;

    @GetMapping("/profile")
    @Tags({@Tag(name = "SPRINT 1"),@Tag(name = "SPRINT 9 - MANAGE")})
    public UserProfile getProfile(@RequestParam(required = false) String id, @RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        if (id==null && email==null && phone==null)
            throw new RequestParamException();
        if (id!=null)
            return userProfileService.findUserById(id);
        if (email!=null)
            return userProfileService.findUserByEmail(email);
        return userProfileService.findUserByPhone(phone);
    }


    @GetMapping("/get-all")
    @Tag(name = "SPRINT 9 - MANAGE")
    public ArrayList<UserEntity> getAll(@RequestParam int page) {
        return userRepository.getAllUser(page, Role.USER);
    }
}
