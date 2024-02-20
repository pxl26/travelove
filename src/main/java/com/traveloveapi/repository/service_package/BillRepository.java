package com.traveloveapi.repository.service_package;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BillEntity find(String id) {
        return entityManager.find(BillEntity.class, id);
    }

    public ArrayList<BillEntity> findByService(String service_id) {
        List temp = entityManager.createQuery("FROM BillEntity  m WHERE m.service_id=:id").setParameter("id",service_id).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findByService(String service_id, Date date) {
        List temp = entityManager.createQuery("FROM BillEntity m WHERE m.service_id=:id AND m.date=:date").setParameter("id",service_id).setParameter("date",date).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findAvailableService(String service_id, Date date) {
        List temp = entityManager.createQuery("FROM BillEntity m WHERE m.service_id=:id AND m.date=:date AND m.status!=:status").setParameter("id",service_id).setParameter("date",date).setParameter("status", BillStatus.CANCELED).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findByUser(String user_id) {
        List temp = entityManager.createQuery("FROM BillEntity  m WHERE m.user_id=:id ORDER BY m.create_at").setParameter("id",user_id).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findByTour(String tour_id) {
        List temp = entityManager.createQuery("FROM BillEntity  m WHERE m.service_id=:id ORDER BY m.create_at").setParameter("id",tour_id).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }
    @Transactional
    public void save(BillEntity entity) {
        entityManager.persist(entity);
    }
}
