package com.traveloveapi.controller.pay_method;

import com.traveloveapi.constrain.PayMethodType;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.pay_method.PayMethodEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.pay_method.PayMethodRepository;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PayMethodController {
    final private UserService userService;
    final private PayMethodRepository payMethodRepository;


    @Tag(name = "SPRINT 10 - MANAGE")
    @GetMapping("/pay-method")
    public List<PayMethodEntity> getPayMethod(@RequestParam(required = false) String user_id) {
        if (user_id != null) {
            if (!userService.isAdmin())
                throw new ForbiddenException();
        }
        else user_id= SecurityContext.getUserID();
        return payMethodRepository.findByUserId(user_id);
    }

    @Tag(name = "SPRINT 10 - MANAGE")
    @PostMapping("/pay-method")
    public PayMethodEntity createPayMethod(@RequestParam PayMethodType type, @RequestParam String name, @RequestParam String code) {
        PayMethodEntity payMethodEntity = new PayMethodEntity();
        List<PayMethodEntity> list = payMethodRepository.findByUserId(SecurityContext.getUserID());
        payMethodEntity.setId(UUID.randomUUID().toString());
        payMethodEntity.setUser_id(SecurityContext.getUserID());
        payMethodEntity.setType(type);
        payMethodEntity.setName(name);
        payMethodEntity.setCode(code);
        payMethodEntity.setPriority(list.size());
        payMethodRepository.save(payMethodEntity);
        return payMethodEntity;
    }
}
