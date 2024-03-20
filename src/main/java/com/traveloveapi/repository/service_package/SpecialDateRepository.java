package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.special_date.SpecialDateEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SpecialDateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<SpecialDateEntity> findByService(String id) {
        return (ArrayList<SpecialDateEntity>) entityManager.createQuery("FROM SpecialDateEntity m WHERE m.service_id=:id").setParameter("id",id).getResultList();
    }

    @Transactional
    public void save(SpecialDateEntity entity) {
        entityManager.persist(entity);
    }
}
