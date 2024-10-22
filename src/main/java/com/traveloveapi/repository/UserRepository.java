package com.traveloveapi.repository;

import com.traveloveapi.constrain.Role;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity find(String id) {
        return entityManager.find(UserEntity.class, id);
    }

    public ArrayList<UserEntity> getAllUser(int page, Role role) {
        int page_size = 10;
        List list = entityManager.createQuery("FROM UserEntity u WHERE u.role=:role").setMaxResults(page_size).setFirstResult(page_size*page).setParameter("role", role).getResultList();
        if (list.isEmpty())
            throw new UserNotFoundException();
        return (ArrayList<UserEntity>) list;
    }
    @Transactional
    public void save(UserEntity entity){
            entityManager.persist(entity);
    }

    @Transactional
    public void update(UserEntity entity){
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(UserEntity entity){
        entityManager.remove(entity);
    }
}
