package com.traveloveapi.controller.admin;

import com.amazonaws.services.kms.model.NotFoundException;
import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.entity.ban.BanEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.UserDetailRepository;
import com.traveloveapi.repository.ban.BanRepository;
import com.traveloveapi.service.email.MailService;
import com.traveloveapi.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ManageOwner {
    final private UserService userService;
    final private BanRepository banRepository;
    final private MailService mailService;
    final private UserDetailRepository userDetailRepository;

    @GetMapping("/all-owner")
    @Tag(name = "MANAGE API")
    public ArrayList<UserEntity> getAllTourOwner(@RequestParam int page) {
        return userService.getAllOwner(page);
    }

    @PostMapping("/ban-owner")
    @Tag(name = "SPRINT 11 - MANAGE")
    public BanEntity banOwner(@RequestParam String  owner_id, @RequestParam String reason, @RequestParam String note) {
        BanEntity banEntity = new BanEntity();
        banEntity.setId(UUID.randomUUID().toString());
        banEntity.setOwner_id(owner_id);
        banEntity.setReason(reason);
        banEntity.setNote(note);
        banEntity.setBan_at(new Timestamp(System.currentTimeMillis()));
        banRepository.save(banEntity);
        banRepository.disableAllTour(owner_id);
        //-----------------
        UserDetailEntity user = userDetailRepository.find(owner_id);
        mailService.sendSuccessfulOwnerRegistration(user.getEmail(), "We regret to notice that your account was suspended.\n" +
                "\n" +
                "<b>Reason:</b>\n" + reason +
                "\n" +
                "\n" +
                "All tours on your own were disabled, which means not any bookings will be able to be placed through Travelove. But all placed bookings are still working, you have to serve for that customer.\n" +
                "\n" +
                "Reply this email if you want to unsuppend your account.\n" +
                "\n" +
                "\n" +
                "Best regards.\n" +
                "\n" +
                "--Travelove admin--", "Your account was suspended");
        return banEntity;
    }

    @PostMapping("/unban")
    @Tag(name = "SPRINT 11 - MANAGE")
    public BanEntity unbanOwner(@RequestParam String  ban_id, @RequestParam String unban_note) {
        BanEntity banEntity = banRepository.findById(ban_id);
        if (banEntity == null)
            throw new NotFoundException("Ban record not found");
        if (banEntity.getUnban_at()!=null)
            throw new CustomException("Owner was unbanned",400);
        banEntity.setUnban_note(unban_note);
        banEntity.setUnban_at(new Timestamp(System.currentTimeMillis()));
        banRepository.update(banEntity);
        return banEntity;
    }
}
