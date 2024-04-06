package com.traveloveapi.repository.ban;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.ban.BanEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BanRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BanEntity findById(String id) {
        return entityManager.find(BanEntity.class, id);
    }

    public List<BanEntity> findAll() {
        return entityManager.createQuery("From BanEntity e ORDER BY e.created_at", BanEntity.class).getResultList();
    }

    @Transactional
    public void save(BanEntity banEntity) {
        entityManager.persist(banEntity);
    }

    @Transactional
    public void delete(BanEntity banEntity) {
        entityManager.remove(banEntity);
    }

    @Transactional
    public void update(BanEntity banEntity) {
        entityManager.merge(banEntity);
    }

    public void disableAllTour(String owner_id) {
        entityManager.createQuery("UPDATE ServiceEntity e SET e.status= :status WHERE e.service_owner = :owner_id").setParameter("status", ServiceStatus.DISABLED).setParameter("owner_id", owner_id).executeUpdate();
    }

    public void enableAllTour(String owner_id) {
        entityManager.createQuery("UPDATE ServiceEntity e SET e.status= :status WHERE e.service_owner = :owner_id").setParameter("status", ServiceStatus.VERIFIED).setParameter("owner_id", owner_id).executeUpdate();
    }
}
