package com.traveloveapi.repository;

import com.traveloveapi.DTO.service.TourOwnerDTO;
import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.entity.ServiceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ServiceEntity find(String id) {
        ServiceEntity rs = entityManager.find(ServiceEntity.class, id);
        if (rs!=null && rs.getStatus()!=ServiceStatus.VERIFIED)
            return null;
        return rs;
    }

//    public List getTourOwnerDTO(String owner_id) {
//        List raw = entityManager.createQuery(
//                "SELECT j.sum, j.id, j.full_name, j.avatar " +
//                        "FROM " +
//                        "((SELECT AVG(e.rating) as sum " +
//                            "FROM ServiceEntity e " +
//                                "WHERE e.service_owner=:owner AND e.sold>0) " +
//                        "JOIN " +
//                        "( SELECT u.id id, u.full_name full_name, u.avatar avatar " +
//                            "FROM UserEntity u " +
//                                "WHERE u.id=:owner)) as j").setParameter("owner", owner_id).getResultList();
//        return raw;
//    }

//    public List<TourOwnerDTO> getTourOwnerDTO(String owner_id) {
//        TypedQuery<TourOwnerDTO> query = entityManager.createQuery(
//                        "SELECT NEW com.traveloveapi.DTO.service.TourOwnerDTO(u.id, u.full_name, u.avatar, j.rating, j.sum) " +
//                                "FROM (" +
//                                "  SELECT AVG(e.rating) as rating, SUM(e.sold) as sum " +
//                                "  FROM ServiceEntity e " +
//                                "  WHERE e.service_owner=:owner AND e.sold > 0" +
//                                ") j " +
//                                "JOIN (" +
//                                "  SELECT u.id as id, u.full_name as full_name, u.avatar as avatar " +
//                                "  FROM UserEntity u " +
//                                "  WHERE u.id = :owner" +
//                                ") u", TourOwnerDTO.class)
//                .setParameter("owner", owner_id);
//
//        return query.getResultList();
//    }

    public TourOwnerDTO getTourOwnerDTO(String owner_id) {
        Object query = entityManager.createQuery(
                        """
                                SELECT cc.id, cc.fullName, cc.avatar, vc.avgRating, vc.totalSold
                                FROM (
                                    SELECT AVG(s.rating) AS avgRating, SUM(s.sold) AS totalSold
                                    FROM ServiceEntity s
                                    WHERE s.service_owner=:owner AND s.sold > 0
                                ) AS vc
                                JOIN (
                                    SELECT u.id AS id, u.full_name AS fullName, u.avatar AS avatar
                                    FROM UserEntity u
                                    WHERE u.id =:owner
                                ) AS cc""")
                .setParameter("owner", owner_id).getSingleResult();
        Object[] row = (Object[]) query;
        return new TourOwnerDTO((String) row[0], (String) row[1],(String) row[2], (double)row[3], (long)row[4]);
    }

    public TourOwnerDTO getTourOwnerDTOByTour(String tour_id) {
        Object query = entityManager.createQuery(
                        """
                                SELECT cc.id, cc.fullName, cc.avatar, vc.avgRating, vc.totalSold
                                FROM (
                                    SELECT AVG(s.rating) AS avgRating, SUM(s.sold) AS totalSold
                                    FROM ServiceEntity s JOIN ServiceEntity t ON t.id=:tour_id AND s.service_owner=t.service_owner AND s.sold>0
                                ) AS vc
                                JOIN (
                                    SELECT u.id AS id, u.full_name AS fullName, u.avatar AS avatar
                                    FROM UserEntity u JOIN ServiceEntity service ON service.id=:tour_id AND service.service_owner=u.id
                                ) AS cc""")
                .setParameter("tour_id", tour_id).getSingleResult();
        Object[] row = (Object[]) query;
        return new TourOwnerDTO((String) row[0], (String) row[1],(String) row[2], (double)row[3], (long)row[4]);
    }

    public ServiceEntity findAdmin(String id) {
        return entityManager.find(ServiceEntity.class, id);
    }
    public ArrayList<ServiceEntity> findByStatus(ServiceStatus status, String owner) {
        if (owner.isEmpty())
            return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status").setParameter("status",status).getResultList();
        return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status and m.service_owner=:owner").setParameter("status",status).setParameter("owner",owner).getResultList();
    }

    public ArrayList<ServiceEntity> findByCity(String city_id) {
        List raw = entityManager.createQuery("FROM ServiceEntity m WHERE m.city_id=:id AND m.status=:status").setParameter("id",city_id).setParameter("status", ServiceStatus.VERIFIED).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public ArrayList<ServiceEntity> findByOwner(String id) {
        List raw = entityManager.createQuery("FROM ServiceEntity m WHERE m.service_owner=:id ORDER BY m.status").setParameter("id",id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public List search(String input, int limit, int offset) {
        return Search.session(entityManager).search(ServiceEntity.class).where(f -> f.match().field("title").matching(input)).fetchHits(20);
    }

    @Transactional
    public void save(ServiceEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(ServiceEntity entity) {
        entityManager.merge(entity);
    }
}
