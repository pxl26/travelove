package com.traveloveapi.repository.pay_method;

import com.traveloveapi.entity.pay_method.PayMethodEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayMethodRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(PayMethodEntity entity) {
        entityManager.persist(entity);
    }

    public PayMethodEntity findById(String id) {
        return entityManager.find(PayMethodEntity.class, id);
    }

    public List<PayMethodEntity> findByUserId(String userId) {
        TypedQuery<PayMethodEntity> query = entityManager.createQuery("FROM PayMethodEntity e WHERE e.user_id = :userId", PayMethodEntity.class).setParameter("userId", userId);
        return query.getResultList();
    }
}
