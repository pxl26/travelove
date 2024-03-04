package com.traveloveapi.repository.service_package;

import com.traveloveapi.DTO.service_package.bill.BillDetailDTO;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.Currency;
import com.traveloveapi.entity.join_entity.JoinBillRow;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        List<Object> data = (List<Object>) entityManager.createQuery("SELECT bill.id, bill.total, bill.create_at, bill.update_at, bill.date, bill.status, bill.quantity, person.type, person.quantity, pk_group.title, option.name, service.title, service.id, service.thumbnail, service.rating, service.sold, detail.currency, bill.user_id FROM BillEntity bill JOIN ServiceEntity service ON bill.service_id=service.id AND bill.id=:id JOIN ServiceDetailEntity detail ON service.id=detail.id JOIN BillDetailPersonTypeEntity person ON bill.id=person.bill_id JOIN BillDetailOptionEntity bill_option ON bill.id=bill_option.bill_id JOIN PackageOptionEntity option ON service.id=option.service_id AND bill_option.group_number=option.group_number AND bill_option.option_number=option.option_number JOIN PackageGroupEntity pk_group ON service.id=pk_group.service_id AND pk_group.group_number=option.group_number").setParameter("id",bill_id).getResultList();
        ArrayList<JoinBillRow> rs = new ArrayList<>();
        for (Object ele: data)
        {
            Object[] row = (Object[]) ele;
            JoinBillRow a = new JoinBillRow((String) row[0], (Float) row[1], (Timestamp) row[2], (Timestamp) row[3], (Date) row[4], (BillStatus) row[5], (int)row[6], (String)row[7], (int)row[8], (String)row[9], (String)row[10], (String)row[11], (String)row[12], (String)row[13], (float)row[14], (int)row[15], (Currency) row[16], (String)row[17]);
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

}
