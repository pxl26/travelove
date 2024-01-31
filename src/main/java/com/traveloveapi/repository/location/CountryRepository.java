package com.traveloveapi.repository.location;

import com.traveloveapi.entity.location.CountryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CountryEntity findById(String id) {
        return entityManager.find(CountryEntity.class, id);
    }

    public CountryEntity findByName(String name) {
        return (CountryEntity) entityManager.createQuery("FROM CountryEntity m WHERE m.country_name=:name").setParameter("name",name).getSingleResult();
    }

    @Transactional
    public void save(CountryEntity entity) {
        entityManager.persist(entity);
    }

    public List getAll() {
        return entityManager.createQuery("FROM CountryEntity m").getResultList();
    }
}
