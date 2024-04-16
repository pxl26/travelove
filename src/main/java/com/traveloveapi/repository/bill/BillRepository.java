package com.traveloveapi.repository.bill;

import com.traveloveapi.DTO.owner.IncomeDTO;
import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.entity.join_entity.JoinBillRow;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BillRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BillEntity find(String id) {
        return entityManager.find(BillEntity.class, id);
    }

    public List getBillDetail(String bill_id) {
        List<Object> data = (List<Object>) entityManager.createQuery(
                """
                SELECT bill.id, bill.total, bill.create_at, bill.update_at, bill.date, bill.status, bill.quantity, person.type, person.quantity, pk_group.title, option.name, service.title, service.id, service.thumbnail, service.rating, service.sold, detail.currency, bill.user_id, bill.feedback_id, bill.gateway_url, bill.pay_method, user.avatar, user.full_name, voucher.voucher_id, voucher.discount_amount
                FROM BillEntity bill
                JOIN ServiceEntity service ON bill.service_id=service.id AND bill.id=:id
                JOIN UserEntity user ON bill.user_id=user.id
                LEFT JOIN BillVoucherEntity voucher ON voucher.bill_id=bill.id
                JOIN ServiceDetailEntity detail ON service.id=detail.id
                JOIN BillDetailPersonTypeEntity person ON bill.id=person.bill_id
                JOIN BillDetailOptionEntity bill_option ON bill.id=bill_option.bill_id
                JOIN PackageOptionEntity option ON service.id=option.service_id AND bill_option.group_number=option.group_number AND bill_option.option_number=option.option_number
                JOIN PackageGroupEntity pk_group ON service.id=pk_group.service_id AND pk_group.group_number=option.group_number""").setParameter("id",bill_id).getResultList();
        ArrayList<JoinBillRow> rs = new ArrayList<>();
        for (Object ele: data)
        {
            Object[] row = (Object[]) ele;
            JoinBillRow a = new JoinBillRow((String) row[0], (Float) row[1], (Timestamp) row[2], (Timestamp) row[3], (Date) row[4], (BillStatus) row[5], (int)row[6], (String)row[7], (int)row[8], (String)row[9], (String)row[10], (String)row[11], (String)row[12], (String)row[13], (float)row[14], (int)row[15], (Currency) row[16], (String)row[17], (String)row[18], (String) row[19], (PayMethod) row[20], (String) row[21], (String) row[22], (String) row[23], row[24]==null ? 0: (float) row[24]);
            rs.add(a);
        }
        return rs;
    }

    public ArrayList<BillEntity> findByService(String service_id) {
        List temp = entityManager.createQuery("FROM BillEntity  m WHERE m.service_id=:id ORDER BY m.create_at DESC").setParameter("id",service_id).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findByService(String service_id, Date date) {
        List temp = entityManager.createQuery("FROM BillEntity m WHERE m.service_id=:id AND m.date=:date ORDER BY m.create_at DESC").setParameter("id",service_id).setParameter("date",date).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<BillEntity> findByUser(String user_id) {
        List temp = entityManager.createQuery("FROM BillEntity  m WHERE m.user_id=:id ORDER BY m.create_at DESC").setParameter("id",user_id).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    @Transactional
    public void save(BillEntity entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(BillEntity entity) {
        entityManager.merge(entity);
    }

    public ArrayList<BillEntity> getAllPending() {
        List temp = entityManager.createQuery("FROM BillEntity m WHERE m.status=:status").setParameter("status", BillStatus.PENDING).getResultList();
        if (temp!=null)
            return (ArrayList<BillEntity>) temp;
        return new ArrayList<>();
    }

    public ArrayList<String> getOptionInBill(String bill_id) {
        List temp = entityManager.createQuery("SELECT option.name FROM BillDetailOptionEntity bill_option JOIN BillEntity bill ON bill.id=:id AND bill.id=bill_option.bill_id JOIN PackageOptionEntity option ON option.service_id=bill.service_id AND option.group_number=bill_option.group_number AND option.option_number=bill_option.option_number").setParameter("id",bill_id).getResultList();
        if (temp==null)
            return new ArrayList<>();
        return (ArrayList<String>) temp;
    }

    public IncomeDTO getIncome(String tour_id, String owner_id, Date from, Date to) {
        List<Object> query = entityManager.createQuery(" SELECT SUM(e.total) as total,e.service_id as owner_id, e.update_at, e.update_at , e.service_id as tour_id  FROM BillEntity e WHERE e.service_id=: tour_id AND e.status=: status AND e.update_at>:from AND e.update_at<:to GROUP BY e.service_id").setParameter("from", from).setParameter("to", to).setParameter("tour_id", tour_id).setParameter("status", BillStatus.PAID).getResultList();
        if (query.isEmpty())
            return null;
        Object [] data = (Object[])query.get(0);
        IncomeDTO result = new IncomeDTO();
        result.setTotal((Double) data[0]);
        result.setFrom(from);
        result.setTo(to);
        result.setOwner_id(owner_id);
        return result;
    }

    public IncomeDTO getIncome(String owner_id, Date from, Date to) {
       List<Object> query = entityManager.createQuery(" SELECT SUM(e.total) FROM BillEntity e JOIN ServiceEntity service ON e.service_id=service.id AND service.service_owner=:owner AND e.status=:status AND e.update_at>:from AND e.update_at<:to GROUP BY service.service_owner").setParameter("status", BillStatus.PAID).setParameter("owner", owner_id).setParameter("from",from).setParameter("to",to).getResultList();
        if (query.isEmpty())
            return null;
        IncomeDTO result = new IncomeDTO();
        result.setTotal((Double)query.get(0));
        result.setFrom(from);
        result.setTo(to);
        result.setOwner_id(owner_id);
        return result;
    }
}
