package com.traveloveapi.repository.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.DTO.voucher.VoucherDTO;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherStatus;
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


    public ArrayList<VoucherEntity> getByCreator(String creator, VoucherTargetType type) {
        List temp;
        if (type!=null)
            temp = entityManager.createQuery("FROM VoucherEntity m WHERE m.creator_id=:creator_id AND m.target_type=:type ORDER BY m.status").setParameter("creator_id", creator).setParameter("type", type).getResultList();
        else
            temp = entityManager.createQuery("FROM VoucherEntity m WHERE m.creator_id=:creator_id ORDER BY m.status").setParameter("creator_id", creator).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) temp;
    }

    public ArrayList<VoucherEntity> getByCreator(String creator) {
        List temp = entityManager.createQuery("FROM VoucherEntity m WHERE m.creator_id=:creator_id ORDER BY m.status").setParameter("creator_id", creator).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) temp;
    }

    public ArrayList<VoucherEntity> getByTarget(VoucherTargetType type, String id) {
        List raw = entityManager.createQuery("FROM VoucherEntity m WHERE m.target_type=:type and m.target_id=:id").setParameter("id", id).setParameter("type", type).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) raw;
    }

    public ArrayList<VoucherEntity> getVoucher(String creator, VoucherTargetType type, String target_id) {
        List temp;
        if (type==VoucherTargetType.ALL)
            temp= entityManager.createQuery("FROM VoucherEntity m WHERE m.creator_id=:creator_id ORDER BY m.status").setParameter("creator_id", creator).getResultList();
        else
            temp = entityManager.createQuery("FROM VoucherEntity m WHERE m.creator_id=:creator_id AND m.target_type=:type AND m.target_id=:target_id ORDER BY m.status").setParameter("creator_id",creator).setParameter("type", type).setParameter("target_id", target_id).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<VoucherEntity>) temp;
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


    public ArrayList<VoucherDTO> getAvailableVoucherForTour(String tour_id, String user_id) {
        List<Object> data = (List<Object>)entityManager.createQuery(
                """
                        SELECT data.id, data.discount_type, data.minimum_spend, data.fixed_discount, data.percent_discount, data.max_discount, data.currency  FROM
                        (SELECT voucher.id id, voucher.discount_type discount_type, voucher.minimum_spend minimum_spend , voucher.fixed_discount fixed_discount, voucher.percent_discount percent_discount,voucher.max_discount max_discount, voucher.currency currency
                        FROM  VoucherEntity voucher WHERE ((voucher.target_id=:tour_id AND voucher.target_type='TOUR') OR voucher.target_type='ALL') AND voucher.status='VERIFIED' AND (now() BETWEEN voucher.start_at AND voucher.end_at) AND voucher.stock>0
                        UNION
                        SELECT voucher.id id, voucher.discount_type type, voucher.minimum_spend minimum_spend , voucher.fixed_discount fixed_discount, voucher.percent_discount percent_discount,voucher.max_discount max_discount, voucher.currency currency
                        FROM CollectionDetailEntity collection_detail JOIN VoucherEntity voucher ON voucher.target_type='COLLECTION' AND collection_detail.service_id=:tour_id AND voucher.status='VERIFIED' AND voucher.stock>0 AND (now() BETWEEN voucher.start_at AND voucher.end_at))
                        as data
                        WHERE data.id NOT IN
                        (SELECT redeem.voucher_id FROM VoucherRedeemEntity redeem WHERE redeem.user_id=:user_id)""").setParameter("tour_id", tour_id).setParameter("user_id", user_id).getResultList();
        ArrayList<VoucherDTO> rs = new ArrayList<>();
        for (Object ele: data)
        {
            Object[] row = (Object[]) ele;
            VoucherDTO a = new VoucherDTO((String) row[0], (VoucherDiscountType) row[1], (float) row[2], (float) row[3], (float) row[4], (float) row[5], (Currency) row[6]);
            rs.add(a);
        }
        return rs;
    }

    public ArrayList<VoucherEntity> getAllPendingVoucher() {
        List raw = entityManager.createQuery("FROM VoucherEntity m WHERE  m.status=:status ORDER BY m.start_at").setParameter("status", VoucherStatus.PENDING).getResultList();
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
