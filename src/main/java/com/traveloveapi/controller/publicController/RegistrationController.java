package com.traveloveapi.controller.publicController;

import com.traveloveapi.DTO.SimpleResponse;
import com.traveloveapi.constrain.OwnerRegistrationStatus;
import com.traveloveapi.entity.owner_registration.TourOwnerRegistrationEntity;
import com.traveloveapi.repository.owner_registration.OwnerRegistrationRepository;
import com.traveloveapi.service.aws.s3.S3FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/tour-owner")
    @Tag(name = "SPRINT 10 - MANAGE")
    public SimpleResponse tourOwnerRegistration(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String office_address, @RequestParam(required = false) String referral_code, @RequestParam String tax_code, @RequestParam MultipartFile license) {
        TourOwnerRegistrationEntity entity = new TourOwnerRegistrationEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(name);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setOffice_address(office_address);
        if (referral_code!=null)
            entity.setReferral_code(referral_code);
        entity.setTax_code(tax_code);
        entity.setStatus(OwnerRegistrationStatus.PENDING);
        entity.setBusiness_license(s3FileService.uploadFile(license, "public/license/", entity.getId()));
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        entity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        ownerRegistrationRepository.save(entity);
        return new SimpleResponse("Registration was submitted");
    }
}
