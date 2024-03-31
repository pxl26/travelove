package com.traveloveapi.repository.owner_registration;

import com.traveloveapi.constrain.OwnerRegistrationStatus;
import com.traveloveapi.entity.owner_registration.TourOwnerRegistrationEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OwnerRegistrationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(TourOwnerRegistrationEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(TourOwnerRegistrationEntity entity) {
        entityManager.merge(entity);
    }

    public TourOwnerRegistrationEntity find(String id) {
        return entityManager.find(TourOwnerRegistrationEntity.class, id);
    }

    public ArrayList<TourOwnerRegistrationEntity> getByStatus(OwnerRegistrationStatus status) {
        List temp = entityManager.createQuery("FROM TourOwnerRegistrationEntity e WHERE e.status=:status ORDER BY e.update_at").setParameter("status", status).getResultList();
        if (temp.isEmpty())
            return new ArrayList<>();
        return (ArrayList<TourOwnerRegistrationEntity>) temp;
    }
}
