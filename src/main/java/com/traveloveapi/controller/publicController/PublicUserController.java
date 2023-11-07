package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.user.UserDTO;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.service.user.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    final private UserProfileService userProfileService;
    @GetMapping
    public UserDTO getUser(@RequestParam String id) {
        UserProfile detailProfile = userProfileService.findUserById(id);
        return new UserDTO(detailProfile);
    }
}
