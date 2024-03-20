package com.traveloveapi.repository.bill;

import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import com.traveloveapi.entity.voucher.VoucherRedeemEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BillDetailPersonTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<BillDetailPersonTypeEntity> findByBill(String bill_id) {
        List raw = entityManager.createQuery("FROM BillDetailPersonTypeEntity m WHERE m.bill_id=:id").setParameter("id",bill_id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<BillDetailPersonTypeEntity>) raw;
    }

    @Transactional
    public void save(BillDetailPersonTypeEntity entity) {
        entityManager.persist(entity);
    }
}
