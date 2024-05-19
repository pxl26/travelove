package com.traveloveapi.repository.notification;

import com.traveloveapi.entity.NotificationEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class NotificationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public NotificationEntity findById(String id) {
        return entityManager.find(NotificationEntity.class, id);
    }

    public List<NotificationEntity> getAll(int page, int size, String consumer_id) {
        return entityManager.createQuery("FROM NotificationEntity e WHERE e.consumer_id=:consumer ORDER BY e.create_at DESC").setParameter("consumer", consumer_id).setFirstResult(page * size).setMaxResults(size).getResultList();
    }

    public List<NotificationEntity> getUnread(String consumer_id) {
        return entityManager.createQuery("FROM NotificationEntity e WHERE e.consumer_id=:consumer AND e.read_at IS NULL ORDER BY e.create_at DESC").setParameter("consumer", consumer_id).getResultList();
    }

    @Transactional
    public void readNotification(Timestamp latest_time, String tour_owner) {
        entityManager.createQuery("UPDATE NotificationEntity e SET read_at=now() WHERE e.consumer_id=:owner AND e.create_at<=:latest").setParameter("latest", latest_time).setParameter("owner",tour_owner).executeUpdate();
    }

    @Transactional
    public void save(NotificationEntity notification) {
        if (notification != null)
            entityManager.persist(notification);
    }
}
