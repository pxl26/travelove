package com.traveloveapi.repository.collection;

import com.traveloveapi.constrain.CollectionDisplay;
import com.traveloveapi.entity.collection.CollectionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

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

    public ArrayList<CollectionEntity> getList(CollectionDisplay type, String ref) {
        ArrayList<CollectionEntity> rs;
        if (type==CollectionDisplay.HOME_PAGE)
            rs = (ArrayList<CollectionEntity>) entityManager.createQuery("FROM CollectionEntity m WHERE m.display_at=:type").setParameter("type",type.toString()).getResultList();
        else
            rs = (ArrayList<CollectionEntity>) entityManager.createQuery("FROM CollectionEntity  m WHERE m.display_at=:type AND m.ref_id=:ref").setParameter("type",type).setParameter("ref",ref).getResultList();

        return rs;
    }
}