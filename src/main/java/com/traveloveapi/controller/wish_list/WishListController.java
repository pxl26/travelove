package com.traveloveapi.controller.wish_list;

import com.traveloveapi.DTO.WishList.WishListDTO;
import com.traveloveapi.entity.ServiceDetailEntity;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.WishListEntity;
import com.traveloveapi.repository.ServiceDetailRepository;
import com.traveloveapi.repository.ServiceRepository;
import com.traveloveapi.repository.WishListRepository;
import com.traveloveapi.service.currency.CurrencyService;
import com.traveloveapi.service.tour.TourService;
import com.traveloveapi.service.wish_list.WishListService;
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
    final private WishListService wishListService;
    final private ServiceDetailRepository serviceDetailRepository;
    final private CurrencyService currencyService;


    @GetMapping
    @Tag(name = "SPRINT 4")
    public List getWishList(@RequestParam int page, @RequestParam(required = false) String currency) {
        int page_size = 7;
        List raw_data =  wishListRepository.find(SecurityContext.getUserID(),page_size*page, page_size);
        ArrayList<WishListDTO> rs = new ArrayList<>();
        if (raw_data==null)
            return rs;
        ArrayList<WishListEntity> data = (ArrayList<WishListEntity>)(raw_data);
        for (WishListEntity entity: data)
        {
            ServiceEntity tour = serviceRepository.find(entity.getService_id());
            ServiceDetailEntity detail = serviceDetailRepository.find(entity.getService_id());
            rs.add(new WishListDTO(entity, tour,detail.getCurrency(), currency, currency==null ? null : currencyService.convert(detail.getCurrency(), currency, (double) tour.getMin_price())));
            }
        return rs;
    }

    @PostMapping
    @Tag(name = "SPRINT 4")
    public WishListEntity addWishService(@RequestParam String service_id) {
        if (wishListService.isWish(SecurityContext.getUserID(), service_id))
        {
            WishListEntity rs = wishListRepository.find(SecurityContext.getUserID(), service_id);
            wishListRepository.delete(rs);
            return rs;
        }
        WishListEntity entity = new WishListEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setService_id(service_id);
        entity.setUser_id(SecurityContext.getUserID());
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        wishListRepository.save(entity);
        return entity;
    }
}
