package com.traveloveapi.repository.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.DTO.voucher.VoucherDTO;
import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.MediaEntity;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoucherRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public VoucherEntity find(String voucher_id) {
        return entityManager.find(VoucherEntity.class, voucher_id);
    }

    public ArrayList<VoucherEntity> getVoucherByTour(String tour_id) {
        List raw = entityManager.createQuery("FROM VoucherEntity m WHERE m.target_type='TOUR' and m.target_id=:id and m.status='VERIFIED' and m.start_at< now() and m.end_at > now()").setParameter("id", tour_id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) raw;
    }

    public ArrayList<VoucherRedeemEntity> getVoucher(String user_id, String voucher_id) {
        List raw = entityManager.createQuery("FROM VoucherRedeemEntity m WHERE m.user_id=:user AND m.voucher_id=:voucher").setParameter("user", user_id).setParameter("voucher", voucher_id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<VoucherRedeemEntity>) raw;
    }


    public ArrayList<VoucherDTO> getAvailableVoucherForTour(String tour_id) {
        List<Object> data = (List<Object>)entityManager.createQuery("SELECT voucher.id, voucher.title FROM CollectionDetailEntity collection  JOIN VoucherEntity voucher ON ((voucher.target_id=:tour_id AND voucher.target_type='TOUR') OR voucher.target_type='ALL') AND voucher.status='VERIFIED' AND voucher.start_at<=now() AND voucher.stock>0 AND voucher.end_at>=now()  UNION SELECT voucher.id, voucher.title  FROM CollectionDetailEntity collection_detail JOIN VoucherEntity voucher ON voucher.target_type='COLLECTION' AND collection_detail.service_id=:tour_id AND voucher.status='VERIFIED' AND voucher.stock>0 AND voucher.start_at<=now() AND voucher.end_at>=now()").setParameter("tour_id", tour_id).getResultList();
        ArrayList<VoucherDTO> rs = new ArrayList<>();
        for (Object ele: data)
        {
            Object[] row = (Object[]) ele;
            VoucherDTO a = new VoucherDTO((String) row[0], (String) row[1]);
            rs.add(a);
        }
        return rs;
    }

    public ArrayList<VoucherEntity> getPendingVoucher(String tour_id) {
        List raw = entityManager.createQuery("FROM VoucherEntity m WHERE  m.status='PENDING'").getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) raw;
    }
    @Transactional
    public void save(VoucherEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(VoucherEntity entity) {
        entityManager.merge(entity);
    }
}
