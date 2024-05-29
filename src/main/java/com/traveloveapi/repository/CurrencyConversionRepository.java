package com.traveloveapi.repository;

import com.traveloveapi.entity.CurrencyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrencyConversionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(CurrencyEntity currencyEntity) {
        entityManager.persist(currencyEntity);
    }

    public CurrencyEntity getCurrencyConversion(String currency_name) {
        return entityManager.find(CurrencyEntity.class, currency_name);
    }

    @Transactional
    public void update(CurrencyEntity currencyEntity) {
        entityManager.merge(currencyEntity);
    }

    public List<CurrencyEntity> getAll() {
        return entityManager.createQuery("FROM CurrencyEntity ", CurrencyEntity.class).getResultList();
    }
}
