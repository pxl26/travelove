package com.traveloveapi.repository.collection;

import com.traveloveapi.entity.collection.CollectionDetailEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CollectionDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(CollectionDetailEntity entity) {
        entityManager.persist(entity);
    }

    public ArrayList<CollectionDetailEntity> find(String collection_id) {
        return (ArrayList<CollectionDetailEntity>) entityManager.createQuery("FROM CollectionDetailEntity m WHERE m.collection_id=:id ORDER BY m.seq").setParameter("id", collection_id).getResultList();
    }
}
