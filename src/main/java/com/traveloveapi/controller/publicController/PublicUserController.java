package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.user.UserDTO;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.service.user.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    final private UserProfileService userProfileService;
    //final private RedisConfig redisConfig;

    @GetMapping
    @Tag(name = "SPRINT 1")
    @Operation(description = "Get specified user's information by user_id (brief data)")
    public UserDTO getUser(@RequestParam String id) {
        UserProfile detailProfile = userProfileService.findUserById(id);
        return new UserDTO(detailProfile);
    }

    @GetMapping("/check/email")
    @Tag(name = "SPRINT 1")
    private SimpleResponse getEmailStatus(@RequestParam String email) {
        return new SimpleResponse(userProfileService.checkEmailAndPasswordStatus(email).toString(), 200);
    }
}
