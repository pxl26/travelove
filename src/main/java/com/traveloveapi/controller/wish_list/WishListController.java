package com.traveloveapi.controller.wish_list;

import com.traveloveapi.DTO.WishList.WishListDTO;
import com.traveloveapi.entity.WishListEntity;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.WishListRepository;
import com.traveloveapi.utility.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish-list")
public class WishListController {
    final private WishListRepository wishListRepository;
    final private ServiceRepository serviceRepository;


    @GetMapping
    @Tag(name = "SPRINT 4: Wish list")
    public List getWishList(@RequestParam int page) {
        int page_size = 7;
        List raw_data =  wishListRepository.find(SecurityContext.getUserID(),page_size*page, page_size);
        ArrayList<WishListDTO> rs = new ArrayList<>();
        if (raw_data==null)
            return rs;
        ArrayList<WishListEntity> data = (ArrayList<WishListEntity>)(raw_data);
        for (WishListEntity entity: data)
            rs.add(new WishListDTO(entity,serviceRepository.find(entity.getService_id())));
        return rs;
    }

    @PostMapping
    @Tag(name = "SPRINT 4: Wish list")
    public WishListEntity addWishService(@RequestParam String service_id) {
        WishListEntity entity = new WishListEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setService_id(service_id);
        entity.setUser_id(SecurityContext.getUserID());
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        wishListRepository.save(entity);
        return entity;
    }
}
