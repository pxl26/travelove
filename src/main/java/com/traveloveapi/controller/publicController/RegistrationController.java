package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.DTO.TokenResponse;
import com.traveloveapi.constrain.OwnerRegistrationStatus;
import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.owner_registration.TourOwnerRegistrationEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.owner_registration.OwnerRegistrationRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import com.traveloveapi.utility.JwtProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/registration")
public class RegistrationController {
    final private OwnerRegistrationRepository ownerRegistrationRepository;
    final private S3FileService s3FileService;
    final private UserDetailRepository userDetailRepository;
    final private PasswordEncoder passwordEncoder;

    @PostMapping("/tour-owner")
    @Tag(name = "SPRINT 10 - MANAGE")
    public SimpleResponse tourOwnerRegistration(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String office_address, @RequestParam String tax_code, @RequestParam MultipartFile license, @RequestParam String contact_name, @RequestParam String contact_language, @RequestParam MultipartFile insurance_policy) {
        //-----VALIDATE EMAIL-----
        TourOwnerRegistrationEntity check =  ownerRegistrationRepository.findByEmail(email);
        if (check != null) {
            if (check.getStatus()==OwnerRegistrationStatus.PENDING)
                throw new CustomException("EMAIL: PENDING REGISTRATION", 400);
            if (check.getStatus()==OwnerRegistrationStatus.ACCEPTED)
                throw new CustomException("EMAIL: ACCEPTED REGISTRATION", 400);
        }

        UserDetailEntity userDetailEntity = userDetailRepository.findEmail(email);
        if (userDetailEntity!=null)
            throw new CustomException("EMAIL: ALREADY EXISTED", 400);

        TourOwnerRegistrationEntity entity = new TourOwnerRegistrationEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setCompany_name(name);
        entity.setContact_name(contact_name);
        entity.setContact_language(contact_language);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setAddress(office_address);
        entity.setTax_code(tax_code);
        entity.setStatus(OwnerRegistrationStatus.PENDING);
        entity.setInsurance_policy(s3FileService.uploadFile(insurance_policy, "public/registration/", entity.getId() + "_insurance"));
        entity.setBusiness_registration(s3FileService.uploadFile(license, "public/registration/", entity.getId()+"_registration"));
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        entity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        ownerRegistrationRepository.save(entity);
        return new SimpleResponse("Registration was submitted");
    }
    @PostMapping("/new-password")
    @Tag(name = "SPRINT 10 - MANAGE")
    public TokenResponse newPassword(@RequestParam String registration_id, @RequestParam String user_id, @RequestParam String password) {
        TourOwnerRegistrationEntity entity = ownerRegistrationRepository.find(registration_id);
        if (entity==null)
            throw new CustomException("Registration not found", 404);
        if (entity.getStatus()==OwnerRegistrationStatus.PENDING)
            throw new CustomException("Registration have not been verify", 400);
        if (entity.getStatus()==OwnerRegistrationStatus.REFUSED)
            throw new CustomException("Registration have been refused", 400);
        UserDetailEntity detail = userDetailRepository.find(user_id);
        if (detail.getEmail().equals(entity.getEmail()))
            detail.setPassword(passwordEncoder.encode(password));

        userDetailRepository.update(detail);
        return JwtProvider.generateTokenResponse(detail.getUser_id(), Role.TOUR_OWNER);
    }
}
