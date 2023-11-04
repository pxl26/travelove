package com.traveloveapi.service.user;

import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.constrain.Roles;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;

    public UserProfile findUserById(String id) {
        UserDetailEntity detail = userDetailRepository.find(id);
        return getUserProfile(detail);
    }

    public UserProfile findUserByPhone(String phone) {
        UserDetailEntity detail = userDetailRepository.findByPhone(phone);
        return getUserProfile(detail);
    }

    public UserProfile findUserByEmail(String email) {
        UserDetailEntity detail = userDetailRepository.findByEmail(email);
        return getUserProfile(detail);
    }

    private UserProfile getUserProfile(UserDetailEntity detail) {
        UserEntity user = userRepository.find(detail.getUser_id());
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());
        userProfile.setFull_name(user.getFull_name());
        userProfile.setAvatar(user.getAvatar());
        userProfile.setRegion(detail.getRegion());
        userProfile.setRole(user.getRole());
        userProfile.setBirth(detail.getBirth());
        userProfile.setGender(detail.getGender());
        userProfile.setCreate_at(detail.getCreate_at());
        userProfile.setUsername(detail.getUsername());
        //------ONLY ADMIN AND ACCOUNT OWNER ABLE TO LOAD PROFILE

        if(userProfile.getId().equals(SecurityContext.getUserID())) //account owner
            return userProfile;

        UserEntity cur_user = userRepository.find(userProfile.getId());
        if (cur_user.getRole()== Roles.ADMIN)
            return userProfile;
        throw new ForbiddenException();
    }
}
