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

    @Transactional
    public void update(MediaEntity entity) {
        entityManager.merge(entity);
    }
    //    ----------------------------------------

    public ArrayList<MediaEntity> find(String ref_id, String type) {
        return (ArrayList<MediaEntity>) entityManager.createQuery("FROM MediaEntity m WHERE m.ref_id=:ref AND m.type=:type ORDER BY m.seq").setParameter("ref",ref_id).setParameter("type",type).getResultList();
    }

    @Transactional
    public void delete(String ref_id, String type) {
        entityManager.createQuery("DELETE FROM MediaEntity e WHERE e.ref_id=:ref AND e.type=:type").setParameter("ref", ref_id).setParameter("type", type).executeUpdate();
    }
}
