package com.traveloveapi.controller;

import com.traveloveapi.DTO.owner.IncomeDTO;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.ForbiddenException;
import com.traveloveapi.repository.bill.BillRepository;
import com.traveloveapi.service.user.UserService;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/tour-owner")
public class OwnerController {
    final private BillRepository billRepository;
    final private UserService userService;

    @GetMapping("/income")
    @Tag(name = "TOUR OWNER")
    public IncomeDTO getIncome(@RequestParam(required = false) String owner_id, @RequestParam(required = false) String tour_id, @RequestParam Date from, @RequestParam Date to) {
        UserEntity user;
        if (owner_id!=null){
            if(!userService.isAdmin())
                throw new ForbiddenException();
            user = userService.getUser(owner_id);
        }
        else
            user = userService.getUser(SecurityContext.getUserID());
        IncomeDTO rs;
        if (tour_id!=null) {
            if(!userService.verifyIsOwner(tour_id, user.getId()))
                throw new ForbiddenException();
            rs = billRepository.getIncome(tour_id, user.getId(), from, to);
        }
        else
            rs =  billRepository.getIncome(user.getId(), from, to);

        if (rs == null)
            rs = new IncomeDTO(0.0,user.getId(), from, to, tour_id);
        return rs;
    }
}
