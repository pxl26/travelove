package com.traveloveapi.repository.service_package;

import com.traveloveapi.entity.service_package.option_special.OptionSpecialEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class OptionSpecialRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ArrayList<OptionSpecialEntity> findByService(String id) {
        return (ArrayList<OptionSpecialEntity>) entityManager.createQuery("FROM OptionSpecialEntity m WHERE m.service_id=:id").setParameter("id",id).getResultList();

    }

    public void save(OptionSpecialEntity entity) {
        entityManager.persist(entity);
    }
}
