package com.traveloveapi.repository.service_package;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.entity.ServiceEntity;
import com.traveloveapi.entity.join_entity.JoinBillDetail;
import com.traveloveapi.entity.join_entity.Test;
import com.traveloveapi.entity.service_package.bill.BillEntity;
import com.traveloveapi.entity.service_package.bill_detail_person_type.BillDetailPersonTypeEntity;
import com.traveloveapi.entity.service_package.package_group.PackageGroupEntity;
import com.traveloveapi.entity.service_package.package_option.PackageOptionEntity;
import com.traveloveapi.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Date;
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
//        List rs = entityManager.createQuery("(SELECT )(SELECT bill,service FROM BillEntity bill JOIN ServiceEntity service ON service.id=bill.service_id WHERE bill.id=:id)").setParameter("id", bill_id).getResultList();
//        List rs = entityManager.createQuery("SELECT bill, service, person, option, pk_group FROM BillEntity bill JOIN ServiceEntity service ON bill.service_id=service.id AND bill.id=:id JOIN BillDetailPersonTypeEntity person ON bill.id=person.bill_id JOIN BillDetailOptionEntity bill_option ON bill.id=bill_option.bill_id JOIN PackageOptionEntity option ON service.id=option.service_id AND bill_option.group_number=option.group_number AND bill_option.option_number=option.option_number JOIN PackageGroupEntity pk_group ON service.id=pk_group.service_id AND pk_group.group_number=option.group_number").setParameter("id",bill_id).getResultList();
        ArrayList<Object> rs = (ArrayList<Object>) entityManager.createQuery("SELECT bill, service, person, option, pk_group FROM BillEntity bill JOIN ServiceEntity service ON bill.service_id=service.id AND bill.id=:id JOIN BillDetailPersonTypeEntity person ON bill.id=person.bill_id JOIN BillDetailOptionEntity bill_option ON bill.id=bill_option.bill_id JOIN PackageOptionEntity option ON service.id=option.service_id AND bill_option.group_number=option.group_number AND bill_option.option_number=option.option_number JOIN PackageGroupEntity pk_group ON service.id=pk_group.service_id AND pk_group.group_number=option.group_number").setParameter("id",bill_id).getResultList();
        ArrayList<JoinBillDetail> result = new ArrayList<>();
        for (Object ele: rs)
        {
            try {
//                Class<?> clazz = ele.getClass();
//                Field field = BillEntity.class.getField("id");
//                Object fieldValue = field.get(ele);
//                System.out.println("ID la: " + field.toString());
                if (ele instanceof ArrayList<?>)
                    System.out.println("DUNG ROI NE");
                if (ele instanceof BillEntity)
                    System.out.println("DUNG ROI NE");
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
             //Note, this can throw an exception if the field doesn't exist.
            ;//            ServiceEntity tour = (ServiceEntity) ele.get(1);
//            BillDetailPersonTypeEntity person = (BillDetailPersonTypeEntity) ele.get(2);
//            PackageOptionEntity option = (PackageOptionEntity) ele.get(3);
//            PackageGroupEntity group = (PackageGroupEntity) ele.get(4);
        }
        System.out.println("size: " + rs.size());
        ArrayList<BillDetailPersonTypeEntity> temp = new ArrayList<>();
        return result;
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
