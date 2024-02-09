package com.traveloveapi.repository;

import com.traveloveapi.entity.WishListEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List find(String user_id, int offset, int limit) {
        Query query = entityManager.createQuery("FROM WishListEntity m WHERE m.user_id=:user ORDER BY m.create_at").setParameter("user", user_id);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List find(String user_id) {
        Query query = entityManager.createQuery("FROM WishListEntity m WHERE m.user_id=:user ORDER BY m.create_at").setParameter("user", user_id);
        return query.getResultList();
    }

    @Transactional
    public void save(WishListEntity entity) {
        entityManager.persist(entity);
    }
}
