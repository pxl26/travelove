package com.traveloveapi.service.user;

import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.Gender;
import com.traveloveapi.constrain.Region;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.service.file.FileService;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final FileService fileService;
    private final UserProfileService userProfileService;
    public UserProfile updateProfile(String full_name, Gender gender, Region region, Date birth, MultipartFile avatar) {
        UserDetailEntity detail = userDetailRepository.find(SecurityContext.getUserID());
        detail.setGender(gender!=null ? gender : detail.getGender());
        detail.setBirth(birth!=null ? birth : detail.getBirth());
        detail.setRegion(region!=null ? region : detail.getRegion());
        userDetailRepository.save(detail);

        UserEntity user = userRepository.find(SecurityContext.getUserID());
        user.setFull_name(full_name!=null ? full_name : user.getFull_name());
        if (avatar!=null)
            user.setAvatar(fileService.savePublicImage(avatar));
        userRepository.save(user);

        return userProfileService.findUserById(SecurityContext.getUserID());
    }
}
