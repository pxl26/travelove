package com.traveloveapi.repository;

import com.traveloveapi.entity.ServiceDetailEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ServiceDetailEntity find(String id) {
        return entityManager.find(ServiceDetailEntity.class, id);
    }

    @Transactional
    public void save(ServiceDetailEntity entity) {
        entityManager.persist(entity);
    }


    @Transactional
    public void update(ServiceDetailEntity entity) {
        entityManager.merge(entity);
    }
    //------------------------
}
