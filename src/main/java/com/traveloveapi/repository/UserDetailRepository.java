package com.traveloveapi.repository;

import com.traveloveapi.entity.UserDetailEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserDetailEntity find(String id) {
        return entityManager.find(UserDetailEntity.class, id);
    }

    public UserDetailEntity findByEmail(String email) { return (UserDetailEntity) entityManager.createQuery("FROM UserDetailEntity u WHERE u.email=:email").setParameter("email",email).getSingleResult();}

    @Transactional
    public void save(UserDetailEntity entity) {
        entityManager.persist(entity);
    }
}
