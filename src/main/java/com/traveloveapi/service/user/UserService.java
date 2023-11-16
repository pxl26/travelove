package com.traveloveapi.service.user;

import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.exception.IncorrectPasswordException;
import com.traveloveapi.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

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
}
