package com.traveloveapi.repository;

import com.traveloveapi.entity.MediaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.mapping.List;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MediaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public MediaEntity find(String id) {
        return entityManager.find(MediaEntity.class, id);
    }

    @Transactional
    public void save(MediaEntity entity) {
        entityManager.persist(entity);
    }
    //    ----------------------------------------

    public ArrayList<MediaEntity> find(String ref_id, String type) {
        return (ArrayList<MediaEntity>) entityManager.createQuery("FROM MediaEntity m WHERE m.ref_id=:ref AND m.type=:type").setParameter("ref",ref_id).setParameter("type",type).getResultList();
    }
}