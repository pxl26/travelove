package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.bill_detail_option.BillDetailOptionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BillDetailOptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<BillDetailOptionEntity> findByBill(String bill_id) {
        return (ArrayList<BillDetailOptionEntity>) entityManager.createQuery("FROM BillDetailOptionEntity m WHERE m.bill_id=:id").setParameter("id",bill_id).getResultList();
    }

    @Transactional
    public void save(BillDetailOptionEntity entity) {
        entityManager.persist(entity);
    }
}
