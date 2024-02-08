package com.traveloveapi.repository;

import com.traveloveapi.constrain.UserAction;
import com.traveloveapi.entity.logging.ActivityLogEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityLogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(ActivityLogEntity entity) {
        entityManager.persist(entity);
    }

    public ArrayList<ActivityLogEntity> get(String user_id, int offset, int limit, UserAction action) {
        Query query = entityManager.createQuery("FROM ActivityLogEntity m WHERE  m.user_id=:user AND m.action=:action ORDER BY m.time DESC ").setParameter("user",user_id).setParameter("action", action);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (ArrayList<ActivityLogEntity>) query.getResultList();
    }

    public ArrayList<ActivityLogEntity> get(String user_id, int offset, int limit) {
        Query query = entityManager.createQuery("FROM ActivityLogEntity m WHERE  m.user_id=:user ORDER BY m.time").setParameter("user",user_id);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return (ArrayList<ActivityLogEntity>) query.getResultList();
    }
}
