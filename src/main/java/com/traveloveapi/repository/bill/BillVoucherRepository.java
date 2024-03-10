package com.traveloveapi.repository.bill;

import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherEntity;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherID;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BillVoucherRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BillVoucherEntity find(String bill_id, String voucher_id) {
        BillVoucherID id = new BillVoucherID(bill_id, voucher_id);
        return entityManager.find(BillVoucherEntity.class, id);
    }

    public ArrayList<BillVoucherEntity> findByBill(String bill_id) {
        List raw = entityManager.createQuery("FROM BillVoucherEntity m WHERE m.bill_id=:bill").setParameter("bill", bill_id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<BillVoucherEntity>) raw;
    }

    @Transactional
    public void save(BillVoucherEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(BillVoucherEntity entity) {
        entityManager.merge(entity);
    }
}
