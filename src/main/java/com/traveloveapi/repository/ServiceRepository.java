package com.traveloveapi.repository;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.ServiceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ServiceEntity find(String id) {
        return entityManager.find(ServiceEntity.class, id);
    }
    public ArrayList<ServiceEntity> findByStatus(ServiceStatus status, String owner) {
        if (owner.isEmpty())
            return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status").setParameter("status",status).getResultList();
        return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status and m.service_owner=:owner").setParameter("status",status).setParameter("owner",owner).getResultList();
    }

    @Transactional
    public void save(ServiceEntity entity) {
        entityManager.persist(entity);
    }
}
