package com.traveloveapi.repository.searching;

import com.traveloveapi.entity.searching.SearchingEntity;
import com.traveloveapi.utility.SearchingSupporter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(SearchingEntity entity) {
        entityManager.persist(entity);
    }

    public List<SearchingEntity> findByTitle(String input, int offset, int limit) {
        System.out.println("SEARCH REPO");
        return Search.session(entityManager).search(SearchingEntity.class).where(f->f.match().field("data").matching(input)).fetchHits(offset, limit);
    }

    public SearchingEntity find(String ref_id) {
        return entityManager.find(SearchingEntity.class, ref_id);
    }

    public ArrayList<SearchingEntity> getByCity(String city_name) {
        List raw = entityManager.createQuery("FROM SearchingEntity m WHERE m.city_name=:name").setParameter("name",city_name).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<SearchingEntity>) raw;
    }

    public ArrayList<SearchingEntity> getByCountry(String country_name) {
        List raw = entityManager.createQuery("FROM SearchingEntity m WHERE m.country_name=:name").setParameter("name",country_name).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<SearchingEntity>) raw;
    }
}
