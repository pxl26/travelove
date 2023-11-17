package com.traveloveapi.service.user;

import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.exception.IncorrectPasswordException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.utility.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean updateUserPassword(String old_password,String new_password, String user_id) {
        UserDetailEntity detail = userDetailRepository.find(user_id);

        if (detail.getPassword()!=null && old_password==null)
            return false;

        if (detail.getPassword()==null || passwordEncoder.matches(old_password, detail.getPassword()))  //password is match
        {
            detail.setPassword(passwordEncoder.encode(new_password));
            userDetailRepository.save(detail);
            return true;
        }
        else
            throw new IncorrectPasswordException();
    }

    public UserEntity verifyIsAdmin() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        if (user.getRole()!= Role.ADMIN)
            throw new ForbiddenException();
        return user;
    }

    public UserEntity verifyIsOwner() {
        UserEntity user = userRepository.find(SecurityContext.getUserID());
        if (user.getRole()!= Role.TOUR_OWNER)
            throw new ForbiddenException();
        return user;
    }
}
