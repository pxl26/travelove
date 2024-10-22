package com.traveloveapi.controller.admin;

import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.DTO.user.UserDTO;
import com.traveloveapi.DTO.user.UserProfile;
import com.traveloveapi.constrain.OwnerRegistrationStatus;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.constrain.voucher.VoucherAuditAction;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.owner_registration.TourOwnerRegistrationEntity;
import com.traveloveapi.entity.pay_method.PayMethodEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.exception.RequestParamException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.repository.owner_registration.OwnerRegistrationRepository;
import com.traveloveapi.service.email.MailService;
import com.traveloveapi.service.register.RegisterService;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.HTMLTemplate;
import com.traveloveapi.utility.JwtProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
public class CreateAccount {
    final private UserService userService;
    final private UserRepository userRepository;
    final private UserDetailRepository userDetailRepository;
    final private PasswordEncoder passwordEncoder;
    final private OwnerRegistrationRepository ownerRegistrationRepository;
    final private MailService mailService;

    @Value("${web.host}")
    private String web_host;
    @PostMapping
    @Tag(name = "SPRINT 1")
    public UserProfile createNewAccount(@RequestParam(required = false) String email, @RequestParam(required = false) String username, @RequestParam Role role, @RequestParam String password){
        userService.verifyIsAdmin();
        if (email==null && username==null)
            throw new RequestParamException();
        String id = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setRole(role);
        userRepository.save(user);
        UserDetailEntity detail = new UserDetailEntity();
        detail.setUser_id(id);
        detail.setCreate_at(new Timestamp(System.currentTimeMillis()));
        detail.setUsername(username);
        detail.setEmail(email);
        detail.setPassword(passwordEncoder.encode(password));
        userDetailRepository.save(detail);
        return new UserProfile(user,detail);
    }

    @GetMapping("/tour-owner-registration")
    @Tag(name = "SPRINT 10  - MANAGE")
    public ArrayList<TourOwnerRegistrationEntity> getRegistration(@RequestParam OwnerRegistrationStatus status) {
        return ownerRegistrationRepository.getByStatus(status);
    }

    @PostMapping("/tour-owner-registration")
    @Tag(name = "SPRINT 10 - MANAGE")
    public TourOwnerRegistrationEntity verify(@RequestParam String id, @RequestParam VoucherAuditAction action, @RequestParam String refuse_reason) {
        if (action==VoucherAuditAction.DENY && refuse_reason==null)
            throw new CustomException("Refuse reason is missing", 400);
        TourOwnerRegistrationEntity entity = ownerRegistrationRepository.find(id);
        entity.setStatus(action==VoucherAuditAction.VERIFY ? OwnerRegistrationStatus.ACCEPTED : OwnerRegistrationStatus.REFUSED);
        ownerRegistrationRepository.update(entity);
        if (action==VoucherAuditAction.DENY)
        {
            mailService.sendSuccessfulOwnerRegistration(entity.getEmail(),HTMLTemplate.rejectedOwnerRegistration(entity.getCompany_name(), refuse_reason), "Travelove merchant was failure");
            return entity;
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setFull_name(entity.getCompany_name());
        user.setRole(Role.TOUR_OWNER);
        userRepository.save(user);
        UserDetailEntity detail = new UserDetailEntity();
        detail.setCreate_at(new Timestamp(System.currentTimeMillis()));
        detail.setPhone(entity.getPhone());
        detail.setUser_id(user.getId());
        detail.setEmail(entity.getEmail());
        userDetailRepository.save(detail);
        String link = web_host + "/tour-owner-registration/new-password?registration_id=" + entity.getId() + "&user_id=" + user.getId();

        mailService.sendSuccessfulOwnerRegistration(entity.getEmail(), HTMLTemplate.successfulOwnerRegistration(entity.getCompany_name(), link), "Travelove merchant registration successfully");
        return entity;
    }
}
