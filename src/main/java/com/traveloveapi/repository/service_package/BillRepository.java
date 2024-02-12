package com.traveloveapi.repository.service_package;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    public ArrayList<BillEntity> findByService(String service_id) {
        return (ArrayList<BillEntity>) entityManager.createQuery("FROM BillEntity  m WHERE m.service_id=:id").setParameter("id",service_id).getResultList();
    }

    public ArrayList<BillEntity> findByService(String service_id, Date date) {
        return (ArrayList<BillEntity>) entityManager.createQuery("FROM BillEntity m WHERE m.service_id=:id AND m.date=:date").setParameter("id",service_id).setParameter("date",date).getResultList();
    }

    public ArrayList<BillEntity> findAvailableService(String service_id, Date date) {
        return (ArrayList<BillEntity>) entityManager.createQuery("FROM BillEntity m WHERE m.service_id=:id AND m.date=:date AND m.status!=:status").setParameter("id",service_id).setParameter("date",date).setParameter("status", BillStatus.CANCELED).getResultList();
    }

    @Transactional
    public void save(BillEntity entity) {
        entityManager.persist(entity);
    }
}
