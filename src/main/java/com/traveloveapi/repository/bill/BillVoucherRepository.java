package com.traveloveapi.repository.bill;

import com.traveloveapi.DTO.voucher.VoucherDiscountSummary;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherEntity;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillVoucherRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BillVoucherEntity find(String bill_id, String voucher_id) {
        BillVoucherID id = new BillVoucherID(bill_id, voucher_id);
        return entityManager.find(BillVoucherEntity.class, id);
    }


    public List<VoucherDiscountSummary> getTotalVoucherDiscount(String owner_id, Date from, Date to) {
        List<Object> raw = entityManager.createQuery(
                """
                    SELECT SUM(bill_voucher.discount_amount) as amount, bill_voucher.voucher_id, voucher.title as voucher_title 
                    FROM BillVoucherEntity bill_voucher JOIN VoucherEntity voucher ON bill_voucher.voucher_id = voucher.id JOIN BillEntity bill ON bill_voucher.bill_id = bill.id JOIN ServiceEntity service ON bill.service_id = service.id AND service.service_owner=:owner 
                    WHERE bill.date > :from and bill.date < :to
                    GROUP BY bill_voucher.voucher_id
                    """).setParameter("owner",owner_id).setParameter("from", from).setParameter("to",to).getResultList();
        ArrayList<VoucherDiscountSummary> rs = new ArrayList<>();
        if (raw == null) {
            System.out.println("Hello");
            throw new RuntimeException();
        }
        for (Object o : raw) {
            Object[] row = (Object[]) o;
            VoucherDiscountSummary summary = new VoucherDiscountSummary((Double) row[0], (String)row[1], (String)row[2]);
            rs.add(summary);
        }
        return rs;
    }

    public List<VoucherDiscountSummary> getTotalVoucherDiscountByTour(String tour_id, Date from, Date to) {
        List<Object> raw = entityManager.createQuery(
                """
                    SELECT SUM(bill_voucher.discount_amount) as amount, bill_voucher.voucher_id, voucher.title as voucher_title 
                    FROM BillVoucherEntity bill_voucher JOIN VoucherEntity voucher ON bill_voucher.voucher_id = voucher.id JOIN BillEntity bill ON bill_voucher.bill_id = bill.id AND bill.service_id=:tour_id
                    WHERE bill.date > :from and bill.date < :to
                    GROUP BY bill_voucher.voucher_id
                    """).setParameter("tour_id",tour_id).setParameter("from", from).setParameter("to",to).getResultList();
        ArrayList<VoucherDiscountSummary> rs = new ArrayList<>();
        if (raw == null) {
            System.out.println("Hello");
            throw new RuntimeException();
        }
        for (Object o : raw) {
            Object[] row = (Object[]) o;
            VoucherDiscountSummary summary = new VoucherDiscountSummary((Double) row[0], (String)row[1], (String)row[2]);
            rs.add(summary);
        }
        return rs;
    }

    @Transactional
    public void save(BillVoucherEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(BillVoucherEntity entity) {
        entityManager.merge(entity);
    }
}
