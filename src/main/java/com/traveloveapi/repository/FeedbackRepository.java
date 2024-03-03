package com.traveloveapi.repository;

import com.traveloveapi.entity.feedback.FeedbackEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(FeedbackEntity entity) {
        entityManager.persist(entity);
    }

    public FeedbackEntity find(String id) {
        return entityManager.find(FeedbackEntity.class, id);
    }

    public ArrayList<FeedbackEntity> getByTour(String tour_id) {
        List temp = entityManager.createQuery("FROM FeedbackEntity m WHERE m.ref_id=:id").setParameter("id",tour_id).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<FeedbackEntity>) temp;
    }
}
