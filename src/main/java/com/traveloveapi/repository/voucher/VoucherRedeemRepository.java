package com.traveloveapi.repository.voucher;

import com.traveloveapi.DTO.voucher.RedeemVoucherDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import com.traveloveapi.constrain.voucher.VoucherStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import com.traveloveapi.entity.join_entity.JoinBillRow;
import com.traveloveapi.entity.voucher.VoucherEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoucherRedeemRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public VoucherRedeemEntity find(String id) {
        return entityManager.find(VoucherRedeemEntity.class, id);
    }

    public VoucherRedeemEntity findByKey(String key) {
        List temp = entityManager.createQuery("FROM VoucherRedeemEntity m WHERE m.redeem_key=:key").setParameter("key",key).getResultList();
        if (temp==null)
            return null;
        return (VoucherRedeemEntity) temp.get(0);
    }
    public ArrayList<RedeemVoucherDTO> getByUser(String user_id, int page) {
        int page_size = 10;
        List<Object> data = (List<Object>) entityManager.createQuery("SELECT voucher.id, redeem.redeem_key, voucher.title,voucher.detail, redeem.status, redeem.redeem_at, redeem.expire_at, voucher.target_type, voucher.target_id, voucher.discount_type, voucher.fixed_discount, voucher.percent_discount, voucher.minimum_spend, voucher.max_discount, voucher.currency FROM VoucherRedeemEntity redeem JOIN VoucherEntity voucher ON redeem.user_id=:user AND redeem.voucher_id=voucher.id ORDER BY redeem.expire_at").setParameter("user", user_id).setFirstResult(page_size*page).setMaxResults(page_size).getResultList();
        ArrayList<RedeemVoucherDTO> rs = new ArrayList<>();
        for (Object ele: data)
        {
            Object[] row = (Object[]) ele;
            RedeemVoucherDTO a = new RedeemVoucherDTO((String) row[0], (String) row[1],(String) row[2], (String) row[3], (VoucherRedeemStatus) row[4], (Timestamp) row[5], (Timestamp) row[6], (VoucherTargetType) row[7], (String) row[8], (VoucherDiscountType) row[9], (float) row[10], (float) row[11], (float) row[12], (float) row[13], (Currency) row[14]);
            rs.add(a);
        }
        return rs;
    }

    @Transactional
    public void save(VoucherRedeemEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(VoucherRedeemEntity entity) {
        entityManager.merge(entity);
    }
}
