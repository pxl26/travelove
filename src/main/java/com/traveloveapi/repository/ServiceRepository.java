package com.traveloveapi.repository;

import com.traveloveapi.entity.ServiceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ServiceEntity find(String id) {
        return entityManager.find(ServiceEntity.class, id);
    }

    @Transactional
    public void save(ServiceEntity entity) {
        entityManager.persist(entity);
    }
}
