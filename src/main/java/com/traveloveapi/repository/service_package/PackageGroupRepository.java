package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PackageGroupRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PackageGroupEntity find(PackageGroupId id) {
        return entityManager.find(PackageGroupEntity.class, id);
    }
    public ArrayList<PackageGroupEntity> find(String service_id) {
        return (ArrayList<PackageGroupEntity>) entityManager.createQuery("FROM PackageGroupEntity m WHERE m.service_id=:id").setParameter("id",service_id).getResultList();
    }

    @Transactional
    public void save(PackageGroupEntity entity) {
        entityManager.persist(entity);
    }
}
