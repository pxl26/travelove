package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class PackageOptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PackageOptionEntity find(PackageOptionId id) {
        return entityManager.find(PackageOptionEntity.class, id);
    }

    @Transactional
    public void save(PackageOptionEntity entity) {
        entityManager.persist(entity);
    }
}
