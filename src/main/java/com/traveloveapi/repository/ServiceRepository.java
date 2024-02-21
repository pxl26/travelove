package com.traveloveapi.repository;

import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.ServiceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ServiceEntity find(String id) {
        ServiceEntity rs = entityManager.find(ServiceEntity.class, id);
        if (rs!=null && rs.getStatus()!=ServiceStatus.VERIFIED)
            return null;
        return rs;
    }

    public ServiceEntity findAdmin(String id) {
        return entityManager.find(ServiceEntity.class, id);
    }
    public ArrayList<ServiceEntity> findByStatus(ServiceStatus status, String owner) {
        if (owner.isEmpty())
            return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status").setParameter("status",status).getResultList();
        return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status and m.service_owner=:owner").setParameter("status",status).setParameter("owner",owner).getResultList();
    }

    public ArrayList<ServiceEntity> findByCity(String city_id) {
        List raw = entityManager.createQuery("FROM ServiceEntity m WHERE m.city_id=:id AND m.status=:status").setParameter("id",city_id).setParameter("status", ServiceStatus.VERIFIED).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public List search(String input, int limit, int offset) {
        return Search.session(entityManager).search(ServiceEntity.class).where(f -> f.match().field("title").matching(input)).fetchHits(20);
    }

    @Transactional
    public void save(ServiceEntity entity) {
        entityManager.persist(entity);
    }
}
