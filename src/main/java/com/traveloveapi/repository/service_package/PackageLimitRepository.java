package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.limit.PackageLimitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PackageLimitRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private ArrayList<PackageLimitRepository> findByService(String service_id) {
        return (ArrayList<PackageLimitRepository>) entityManager.createQuery("FROM PackageLimitEntity m WHERE m.service_id=:id").setParameter("id",service_id).getResultList();
    }

    @Transactional
    public void save(PackageLimitEntity entity) {
        entityManager.persist(entity);
    }
}
