package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.disable_option.DisableOptionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PackageDisableOptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<DisableOptionEntity> findByService(String service_id) {
        return (ArrayList<DisableOptionEntity>) entityManager.createQuery("FROM DisableOptionEntity d WHERE d.service_id=:id").setParameter("id",service_id).getResultList();
    }

    @Transactional
    public void save(DisableOptionEntity entity) {
        entityManager.persist(entity);
    }
}
