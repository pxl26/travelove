package com.traveloveapi.repository;

import com.traveloveapi.DTO.TourSummary;
import com.traveloveapi.DTO.service.TourOwnerDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.OrderType;
import com.traveloveapi.constrain.ServiceStatus;
import com.traveloveapi.constrain.SortBy;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.service.currency.CurrencyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private CurrencyService currencyService;

    ServiceRepository(CurrencyService curService) {
        this.currencyService = curService;
    }

    public ServiceEntity find(String id) {
        ServiceEntity rs = entityManager.find(ServiceEntity.class, id);
        if (rs!=null && rs.getStatus()!=ServiceStatus.VERIFIED)
            return null;
        return rs;
    }

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
        return new TourOwnerDTO((String) row[0], (String) row[1],(String) row[2], (Double)row[3], (Long)row[4]);
    }

    public ServiceEntity findAdmin(String id) {
        return entityManager.find(ServiceEntity.class, id);
    }
    public ArrayList<ServiceEntity> findByStatus(ServiceStatus status, String owner) {
        return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status and m.service_owner=:owner").setParameter("status",status).setParameter("owner",owner).getResultList();
    }

    public ArrayList<ServiceEntity> findByStatus(ServiceStatus status) {
        return (ArrayList<ServiceEntity>) entityManager.createQuery("FROM ServiceEntity m WHERE m.status=:status").setParameter("status",status).getResultList();
    }

    public ArrayList<ServiceEntity> findByCity(String city_id, OrderType orderType, SortBy orderBy, int page, int page_size) {
        List<ServiceEntity> raw = entityManager.createQuery(
                "FROM ServiceEntity m " +
                        "WHERE m.city_id=:id AND m.status=:status " +
                        "ORDER BY " +
                        "CASE " +
                        "WHEN :orderBy='PRICE' THEN m.min_price " +
                        "WHEN :orderBy='RATING' THEN m.rating " +
                        "WHEN :orderBy='SOLD' THEN m.sold END " + (orderType==OrderType.ASCENDED ? "ASC" : "DESC") + ", m.sold DESC, m.id ASC", ServiceEntity.class).setParameter("id",city_id).setParameter("orderBy", orderBy.toString()).setParameter("status",ServiceStatus.VERIFIED).setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public Long getPageTotalCity(String city_id) {
        return (Long) entityManager.createQuery("SELECT CEILING(COUNT(*)) FROM ServiceEntity m WHERE m.city_id=:id AND m.status=:status").setParameter("id",city_id).setParameter("status", ServiceStatus.VERIFIED).getResultList().get(0);
    }

    public Long getPageTotalTitle(String query) {
        return (Long) entityManager.createQuery("SELECT CEILING(COUNT(*)) FROM ServiceEntity m WHERE m.title LIKE '%" + query + "%' AND m.status=:status").setParameter("status", ServiceStatus.VERIFIED).getResultList().get(0);
    }

    public ArrayList<ServiceEntity> findByCountry(String country_name, OrderType orderType, SortBy orderBy, int page, int page_size) {
        List<ServiceEntity> raw = entityManager.createQuery(
                "FROM ServiceEntity m JOIN CityEntity city ON m.city_id=city.id AND city.country_name=:country AND m.status=:status " +
                        "ORDER BY " +
                        "CASE " +
                        "WHEN :orderBy='PRICE' THEN m.min_price " +
                        "WHEN :orderBy='RATING' THEN m.rating " +
                        "WHEN :orderBy='SOLD' THEN m.sold END " + (orderType==OrderType.ASCENDED ? "ASC" : "DESC") + ", m.sold DESC, m.id ASC" , ServiceEntity.class).setParameter("country",country_name).setParameter("orderBy", orderBy.toString()).setParameter("status",ServiceStatus.VERIFIED).setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public ArrayList<ServiceEntity> findByTitle(String query, OrderType orderType, SortBy orderBy, int page, int page_size) {
        List<ServiceEntity> raw = entityManager.createQuery(
                "FROM ServiceEntity m " +
                        "WHERE m.title LIKE '%" + query + "%' AND m.status=:status " +
                        "ORDER BY " +
                        "CASE " +
                        "WHEN :orderBy='PRICE' THEN m.min_price " +
                        "WHEN :orderBy='RATING' THEN m.rating " +
                        "WHEN :orderBy='SOLD' THEN m.sold END " + (orderType==OrderType.ASCENDED ? "ASC" : "DESC") + ", m.sold DESC, m.id ASC", ServiceEntity.class).setParameter("orderBy", orderBy.toString()).setParameter("status",ServiceStatus.VERIFIED).setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return (ArrayList<ServiceEntity>) raw;
    }

    public Long getPageTotalCountry(String country_name) {
        return (Long) entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntity m JOIN CityEntity city ON m.city_id=city.id AND city.country_name=:country AND m.status=:status ").setParameter("country",country_name).setParameter("status",ServiceStatus.VERIFIED).getResultList().get(0);
    }

    public List<ServiceEntity> findByOwner(String id) {
        List<ServiceEntity> raw = entityManager.createQuery("FROM ServiceEntity m WHERE m.service_owner=:id ORDER BY m.status", ServiceEntity.class).setParameter("id",id).getResultList();
        if (raw.isEmpty())
            return new ArrayList<>();
        return raw;
    }

    public List search(String input, int limit, int offset) {
        return Search.session(entityManager).search(ServiceEntity.class).where(f -> f.match().field("title").matching(input)).fetchHits(20);
    }

    public List getRandomTour(int limit) {
        return entityManager.createQuery("FROM ServiceEntity e WHERE e.status=:status ORDER BY RAND() LIMIT :limit").setParameter("status",ServiceStatus.VERIFIED).setParameter("limit", limit).getResultList();
    }

    @Transactional
    public void save(ServiceEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(ServiceEntity entity) {
        entityManager.merge(entity);
    }

    public List<ServiceEntity> getAll() {
        return entityManager.createQuery("FROM ServiceEntity e WHERE e.status= :status", ServiceEntity.class).setParameter("status", ServiceStatus.VERIFIED).getResultList();
    }

    public List<ServiceEntity> getBestSellers(int quantity) {
        return entityManager.createQuery("FROM ServiceEntity e WHERE e.status=:status ORDER BY e.sold DESC LIMIT :quantity", ServiceEntity.class).setParameter("status", ServiceStatus.VERIFIED).setParameter("quantity",quantity).getResultList();
    }

    public List getBestSellers(String owner) {
        List<Object> rawList = entityManager.createQuery(
                        "SELECT s.id, s.title, s.rating, s.vote_quantity, s.sold, SUM(b.total) AS total_income, s.thumbnail " +
                                "FROM ServiceEntity s " +
                                "JOIN BillEntity b ON b.status=:status AND s.id = b.service_id AND s.service_owner = :owner " +
                                "GROUP BY s.id ")
                .setParameter("owner", owner)
                .setParameter("status", BillStatus.PAID)
                .getResultList();
        List<TourSummary> tourList = rawList.stream().map(e -> {
            Object[] row = (Object[])e;
            TourSummary tour = TourSummary.builder()
                    .tour_id((String)row[0])
                    .tour_title((String)row[1])
                    .rating((Float)row[2])
                    .feedbackQuantity((Integer) row[3])
                    .sold((Integer) row[4])
                    .income((Double) row[5])
                    .thumbnail((String)row[6])
                    .build();
            return tour;
        }).toList();
        return tourList.stream().map(e -> {
            List<BillEntity> billList = entityManager.createQuery("FROM BillEntity e WHERE e.service_id=:tour").setParameter("tour", e.getTour_id()).getResultList();
            Double[] total = new Double[1];
            total[0] = 0.0;
            billList.forEach(q ->{
                total[0]+=currencyService.convert(q.getCurrency(), "VND", q.getTotal());
                System.out.println(q.getId() + ": " +total[0]);
            });
            e.setIncome(total[0]);
            return e;
        }).sorted((a,b) -> a.getIncome() < b.getIncome() ? 1 :-1).toList();
    }
}
