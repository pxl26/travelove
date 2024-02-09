package com.traveloveapi.service.wish_list;

import com.traveloveapi.entity.WishListEntity;
import com.traveloveapi.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class WishListService {
    final private WishListRepository wishListRepository;

    public ArrayList<WishListEntity> getAllWishList(String user_id) {
        return (ArrayList<WishListEntity>) wishListRepository.find(user_id);
    }

    public boolean isWish(String user_id, String tour_id) {
        ArrayList<WishListEntity> list = getAllWishList(user_id);
        for (WishListEntity ele: list)
            if (ele.getService_id().equals(tour_id))
                return true;
        return false;
    }
}
