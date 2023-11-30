package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PackageOptionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PackageOptionEntity find(PackageOptionId id) {
        return entityManager.find(PackageOptionEntity.class, id);
    }

    public ArrayList<PackageOptionEntity> findByService(String id) {
        return (ArrayList<PackageOptionEntity>) entityManager.createQuery("FROM PackageOptionEntity m WHERE m.service_id=:id").setParameter("id",id).getResultList();
    }

    public ArrayList<PackageOptionEntity> findByGroup(String id, int group_number) {
        return (ArrayList<PackageOptionEntity>) entityManager.createQuery("FROM PackageOptionEntity m WHERE m.service_id=:id AND m.group_number=:group").setParameter("id",id).setParameter("group",group_number).getResultList();
    }
    @Transactional
    public void save(PackageOptionEntity entity) {
        entityManager.persist(entity);
    }
}
