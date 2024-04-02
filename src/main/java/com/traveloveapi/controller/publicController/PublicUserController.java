package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.service.TourOwnerDTO;
import com.traveloveapi.DTO.user.UserDTO;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.service.user.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    final private UserProfileService userProfileService;
    final private ServiceRepository serviceRepository;

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

    @GetMapping("/tour-owner")
    @Tag(name = "SPRINT 10")
    private TourOwnerDTO getTourOwnerInfo(@RequestParam(required = false) String owner_id, @RequestParam(required = false) String tour_id) {
        if (owner_id==null&&tour_id==null)
            throw new CustomException("What's fucking data do you need????", 400);
        if (owner_id!=null)
            return serviceRepository.getTourOwnerDTO(owner_id);
        return serviceRepository.getTourOwnerDTOByTour(tour_id);
    }
}
