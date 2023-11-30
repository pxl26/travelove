package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BillDetailPersonTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<BillDetailPersonTypeEntity> findByBill(String bill_id) {
        return (ArrayList<BillDetailPersonTypeEntity>) entityManager.createQuery("FROM BillDetailPersonTypeEntity m WHERE m.bill_id=:id").setParameter("id",bill_id).getResultList();
    }

    @Transactional
    public void save(BillDetailPersonTypeEntity entity) {
        entityManager.persist(entity);
    }
}
