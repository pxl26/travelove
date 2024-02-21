package com.traveloveapi.repository.location;

import com.traveloveapi.entity.location.CityEntity;
import com.traveloveapi.entity.location.CountryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CityEntity findById(String id) {
        return entityManager.find(CityEntity.class, id);
    }

    @Transactional
    public void save(CityEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(CityEntity entity) {
        entityManager.merge(entity);
    }

    public List getAllCityByICountryId(String country_id) {
        return entityManager.createQuery("FROM CityEntity m WHERE m.country_id=:id").setParameter("id",country_id).getResultList();
    }

    public List getAllCityByICountryName(String country_name) {
        return entityManager.createQuery("FROM CityEntity m WHERE m.country_name=:name").setParameter("name",country_name).getResultList();
    }
}
