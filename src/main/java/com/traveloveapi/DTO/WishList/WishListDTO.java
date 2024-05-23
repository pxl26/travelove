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
    private float min_price;
    private String currency;

    public WishListDTO(WishListEntity entity, ServiceEntity service, String cur) {
        id = entity.getId();
        service_id = entity.getService_id();
        create_at = entity.getCreate_at();

        title = service.getTitle();
        thumb = service.getThumbnail();
        min_price = service.getMin_price();
        currency = cur;
    }
}
