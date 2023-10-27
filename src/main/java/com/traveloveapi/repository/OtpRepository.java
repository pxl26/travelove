package com.traveloveapi.repository;

import com.traveloveapi.entity.OtpEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.traveloveapi.entity.OtpEntity;

@Repository
public class OtpRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(OtpEntity entity) {
        entityManager.persist(entity);
    }

    public OtpEntity find (String id) {
        return (OtpEntity) entityManager.createQuery("FROM OtpEntity otp WHERE otp.id=:id").setParameter("id",id).getSingleResult();
    }
}
