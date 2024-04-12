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

    public ArrayList<FeedbackEntity> getByTour(String tour_id, int from, int to, int page) {
        int page_size = 10;
        List temp = entityManager.createQuery("FROM FeedbackEntity m WHERE m.ref_id=:id AND m.rating>=:from AND m.rating<=:to ORDER BY m.create_at DESC").setParameter("id",tour_id).setParameter("from", from).setParameter("to",to).setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<FeedbackEntity>) temp;
    }

    public ArrayList<FeedbackEntity> getHasMedia(String tour_id, int page) {
        int page_size = 10;
        List temp = entityManager.createQuery("FROM FeedbackEntity m WHERE m.ref_id=:id AND m.has_media=true ORDER BY m.create_at DESC").setParameter("id",tour_id).setFirstResult(page_size*page).setMaxResults(page_size).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<FeedbackEntity>) temp;
    }

    @Transactional
    public void delete(FeedbackEntity entity) {
        entityManager.remove(entity);
    }
}
