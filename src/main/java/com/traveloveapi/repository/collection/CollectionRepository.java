package com.traveloveapi.repository.collection;

import com.traveloveapi.entity.collection.CollectionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CollectionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(CollectionEntity entity) {
        entityManager.persist(entity);
    }

    public CollectionEntity find(String id) {
        return entityManager.find(CollectionEntity.class, id);
    }
}
