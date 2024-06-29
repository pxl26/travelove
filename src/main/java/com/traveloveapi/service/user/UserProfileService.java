package com.traveloveapi.service.user;

import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.EmailAndPasswordStatus;
import com.traveloveapi.constrain.Gender;
import com.traveloveapi.entity.GoogleEntity;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.GoogleRepository;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;
    final private S3FileService s3FileService;
    final private GoogleRepository googleRepository;

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

    public EmailAndPasswordStatus checkEmailAndPasswordStatus(String email) {
        UserDetailEntity detail = userDetailRepository.findByEmail(email);
        if (detail.getPassword()!=null)
            return EmailAndPasswordStatus.EMAIL_AND_PASSWORD;
        GoogleEntity googleEntity = googleRepository.find(detail.getUser_id());
        if (googleEntity==null)
            return EmailAndPasswordStatus.WAITING_FOR_RESET;
        return EmailAndPasswordStatus.EMAIL_AND_NO_PASSWORD;
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

        UserEntity cur_user = userRepository.find(SecurityContext.getUserID());
        if (cur_user.getRole()== Role.ADMIN || cur_user.getRole()== Role.TOUR_OWNER)
            return userProfile;
        throw new ForbiddenException();
    }

    public UserProfile updateProfile(String full_name, Gender gender, String region, Date birth, MultipartFile avatar) {
        UserDetailEntity detail = userDetailRepository.find(SecurityContext.getUserID());
        detail.setGender(gender!=null ? gender : detail.getGender());
        detail.setBirth(birth!=null ? birth : detail.getBirth());
        detail.setRegion(region!=null ? region : detail.getRegion());
        userDetailRepository.update(detail);

        UserEntity user = userRepository.find(SecurityContext.getUserID());
        user.setFull_name(full_name!=null ? full_name : user.getFull_name());
        if (avatar!=null)
            user.setAvatar(s3FileService.uploadFile(avatar, "public/avatar/", UUID.randomUUID().toString()));
        userRepository.update(user);

        return findUserById(SecurityContext.getUserID());
    }
}
