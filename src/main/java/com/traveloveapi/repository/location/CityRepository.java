package com.traveloveapi.repository.location;

import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.location.CountryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CityEntity findById(String id) {
        return entityManager.find(CityEntity.class, id);
    }

    @Transactional
    public void save(CountryEntity entity) {
        entityManager.persist(entity);
    }
}
