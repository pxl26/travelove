package com.traveloveapi.DTO.WishList;

import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.WishListEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class WishListDTO {
    private String id;
    private String service_id;
    private Timestamp create_at;
    private String title;
    private String thumb;
    private Double min_price;
    private String originCurrency;
    private String userCurrency;

    public WishListDTO(WishListEntity entity, ServiceEntity service, String originCur, String userCur, Double price) {
        id = entity.getId();
        service_id = entity.getService_id();
        create_at = entity.getCreate_at();

        title = service.getTitle();
        thumb = service.getThumbnail();
        min_price = price==null ? service.getMin_price() : price;
        originCurrency = originCur;
        userCurrency = userCur;
    }
}
