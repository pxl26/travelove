package com.traveloveapi.controller.me.profile;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.Gender;
import com.traveloveapi.constrain.Region;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.service.user.UserProfileService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("/me/profile")
@RequiredArgsConstructor
public class SelfProfileController {
    final private UserProfileService userProfileService;
    final private UserService userService;

    @GetMapping
    public UserProfile getProfile() {
        return userProfileService.findUserById(SecurityContext.getUserID());
    }

    @PutMapping
    public UserProfile updateProfile(@RequestParam(required = false) String full_name, @RequestParam(required = false) Gender gender, @RequestParam(required = false) String region, @RequestParam(required = false)Date birth, @RequestParam(required = false)MultipartFile avatar) {
        if (full_name==null&&gender==null&&region==null&&birth==null&&avatar==null) {
            throw new CustomException("Nothing needs to update?");
        }
        return userProfileService.updateProfile(full_name, gender, region, birth, avatar);
    }

    @PutMapping("/password")
    public SimpleResponse updatePassword(@RequestParam String new_password, @RequestParam(required = false) String old_password) {
        if (userService.updateUserPassword(old_password, new_password, SecurityContext.getUserID()))
            return new SimpleResponse("Successfully", 200);
        else
            return new SimpleResponse("Old password is missing", 400);

    }
}