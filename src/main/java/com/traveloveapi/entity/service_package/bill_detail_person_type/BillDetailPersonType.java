package com.traveloveapi.entity.service_package.bill_detail_person_type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "bill_detail_person_type")
@IdClass(BillDetailPersonType.class)
public class BillDetailPersonType implements Serializable {
    @Id
    private String bill_id;
    @Id
    private String type;
    @Id
    private int quantity;
}
