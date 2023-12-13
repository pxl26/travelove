package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeEntity;
import com.traveloveapi.entity.service_package.person_type.PackagePersonTypeId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PackagePersonTypeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PackagePersonTypeEntity find(PackagePersonTypeId id) {
        return entityManager.find(PackagePersonTypeEntity.class, id);
    }

    public ArrayList<PackagePersonTypeEntity> find(String service_id) {
        return (ArrayList<PackagePersonTypeEntity>) entityManager.createQuery("FROM PackageGroupEntity m WHERE m.service_id=:id").setParameter("id",service_id).getResultList();
    }
    @Transactional
    public void save(PackagePersonTypeEntity entity) {
        entityManager.persist(entity);
    }
}
