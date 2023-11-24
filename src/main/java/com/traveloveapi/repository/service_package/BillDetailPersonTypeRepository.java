package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BillDetailPersonTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<BillDetailPersonType> findByBill(String bill_id) {
        return (ArrayList<BillDetailPersonType>) entityManager.createQuery("FROM BillDetailPersonType m WHERE m.bill_id=:id").setParameter("id",bill_id).getResultList();
    }

    @Transactional
    public void save(BillDetailPersonType entity) {
        entityManager.persist(entity);
    }
}
