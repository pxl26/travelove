package com.traveloveapi.repository;

import com.traveloveapi.entity.GoogleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public GoogleEntity find(String id) {
        return entityManager.find(GoogleEntity.class, id);
    }


    @Transactional
    public void save(GoogleEntity entity) {entityManager.persist(entity);}

    @Transactional
    public void update(GoogleEntity entity) {entityManager.merge(entity);}
}