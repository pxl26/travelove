package com.traveloveapi.controller.admin;

import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ManageOwner {
    final private UserService userService;

    @GetMapping("/all-owner")
    @Tag(name = "MANAGE API")
    public ArrayList<UserEntity> getAllTourOwner() {
        return userService.getAllOwner();
    }
}
