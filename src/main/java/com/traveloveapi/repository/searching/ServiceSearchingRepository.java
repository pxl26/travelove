package com.traveloveapi.repository.searching;

import com.traveloveapi.entity.searching.ServiceSearchingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceSearchingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(ServiceSearchingEntity entity) {
        entityManager.persist(entity);
    }

    public List<ServiceSearchingEntity> findByTitle(String input, int offset, int limit) {
        return Search.session(entityManager).search(ServiceSearchingEntity.class).where(f->f.match().field("title").matching(input)).fetchHits(offset, limit);
    }
}