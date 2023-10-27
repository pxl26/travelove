package com.traveloveapi.repository;

import com.traveloveapi.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity find(String id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Transactional
    public void save(UserEntity entity) { entityManager.persist(entity);}
}
