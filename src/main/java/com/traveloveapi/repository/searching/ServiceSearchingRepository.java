package com.traveloveapi.repository.searching;

import com.traveloveapi.entity.searching.ServiceSearchingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        System.out.println("SEARCH REPO");
        return Search.session(entityManager).search(ServiceSearchingEntity.class).where(f->f.match().field("title").matching(input)).fetchHits(offset, limit);
    }

    public ServiceSearchingEntity find(String service_id) {
        return entityManager.find(ServiceSearchingEntity.class, service_id);
    }

    public ArrayList<ServiceSearchingEntity> getByCity(String city_name) {
        List raw = entityManager.createQuery("FROM ServiceSearchingEntity  m WHERE m.city_name=:name").setParameter("name",city_name).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceSearchingEntity>) raw;
    }
}
