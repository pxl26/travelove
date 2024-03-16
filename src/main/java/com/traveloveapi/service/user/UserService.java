package com.traveloveapi.service.user;

import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.exception.IncorrectPasswordException;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public boolean updateUserPassword(String old_password,String new_password, String user_id) {
        UserDetailEntity detail = userDetailRepository.find(user_id);

        if (detail.getPassword()!=null && old_password==null)
            return false;

        if (detail.getPassword()==null || passwordEncoder.matches(old_password, detail.getPassword()))  //password is match
        {
            detail.setPassword(passwordEncoder.encode(new_password));
            userDetailRepository.update(detail);
            return true;
        }
        else
            throw new IncorrectPasswordException();
    }

    public ArrayList<UserEntity> getAllOwner(int page) {
        if (!isAdmin())
            throw new ForbiddenException();
        return userRepository.getAllUser(page, Role.TOUR_OWNER);
    }

    public UserEntity verifyIsAdmin() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        if (user.getRole()!= Role.ADMIN)
            throw new ForbiddenException();
        return user;
    }

    public boolean isAdmin() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        return user.getRole() == Role.ADMIN;
    }

    public UserEntity verifyIsTourOwner() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        if (user.getRole()!= Role.TOUR_OWNER)
            throw new ForbiddenException();
        return user;
    }

    public boolean verifyIsOwner(String tour_id, String user_id) {
        return serviceRepository.findAdmin(tour_id).getService_owner().equals(user_id);
    }
}
