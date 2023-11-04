package com.traveloveapi.controller.user;

import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.exception.RequestParamException;
import com.traveloveapi.service.user.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class ProfileUserController {
    final private UserProfileService userProfileService;
    @GetMapping
    public UserProfile getProfile(@RequestParam(required = false) String id, @RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        if (id==null && email==null && phone==null)
            throw new RequestParamException();
        if (id!=null)
            return userProfileService.findUserById(id);
        if (email!=null)
            return userProfileService.findUserByEmail(email);
        return userProfileService.findUserByPhone(phone);
    }

    @PutMapping
    public UserProfile editProfile(@RequestParam(required = false) String full_name, @RequestParam(required = false) String gender, @RequestParam(required = false) String region) {
        return null;
    }
}
