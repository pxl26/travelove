package com.traveloveapi.repository;

import com.traveloveapi.entity.TourEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class TourRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public TourEntity find(String id) {
        return entityManager.find(TourEntity.class, id);
    }

    public void save(TourEntity entity) {
        entityManager.persist(entity);
    }
}
