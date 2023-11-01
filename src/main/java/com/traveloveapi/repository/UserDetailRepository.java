package com.traveloveapi.repository;

import com.traveloveapi.entity.UserDetailEntity;
import com.traveloveapi.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDetailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserDetailEntity find(String id) {
        return entityManager.find(UserDetailEntity.class, id);
    }

    public UserDetailEntity findByEmail(String email) {
        List<Object> result = entityManager.createQuery("FROM UserDetailEntity u WHERE u.email=:email").setParameter("email",email).getResultList();
        if (result.isEmpty())
            throw new UserNotFoundException();
        return (UserDetailEntity) result.get(0);
    }

    public UserDetailEntity findByUsername(String username) {return (UserDetailEntity) entityManager.createQuery("FROM UserDetailEntity u WHERE u.username=:username").setParameter("username", username).getSingleResult();}
    @Transactional
    public void save(UserDetailEntity entity) {
        entityManager.persist(entity);
    }
}
