package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.bill.BillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BillRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BillEntity find(String id) {
        return entityManager.find(BillEntity.class, id);
    }

    public ArrayList<BillEntity> findByUser(String user_id) {
        return (ArrayList<BillEntity>) entityManager.createQuery("FROM BillEntity  bill WHERE bill.user_id=:id").setParameter("id",user_id).getResultList();
    }

    public void save(BillEntity entity) {
        entityManager.persist(entity);
    }
}
