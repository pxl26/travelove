package com.traveloveapi.repository;

import com.traveloveapi.entity.TourEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TourRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public TourEntity find(String id) {
        return entityManager.find(TourEntity.class, id);
    }

    @Transactional
    public void save(TourEntity entity) {
        entityManager.persist(entity);
    }

    //------------------------
}
