package com.traveloveapi.repository.report;

import com.traveloveapi.constrain.ReportStatus;
import com.traveloveapi.entity.report.ReportEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(ReportEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(ReportStatus status, String report_id) {
        entityManager.createQuery("UPDATE ReportEntity SET status = :status WHERE id = :id").setParameter("status", status).setParameter("id", report_id).executeUpdate();
    }

    public List<ReportEntity> findAll(int page) {
        int page_size = 20;
        TypedQuery<ReportEntity> query = entityManager.createQuery("FROM ReportEntity e ORDER BY e.update_at", ReportEntity.class);
        return query.setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
    }

    public List<ReportEntity> findAll(int page, ReportStatus status) {
        int page_size = 20;
        TypedQuery<ReportEntity> query = entityManager.createQuery("FROM ReportEntity e WHERE e.status= :status ORDER BY e.update_at", ReportEntity.class).setParameter("status", status);
        return query.setFirstResult(page*page_size).setMaxResults(page_size).getResultList();
    }
}
