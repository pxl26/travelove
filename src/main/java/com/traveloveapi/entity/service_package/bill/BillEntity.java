package com.traveloveapi.entity.service_package.bill;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bill")
@Data
public class BillEntity {
    @Id
    private String id;
    private String service_id;
    private String user_id;
    private float total;
    private Timestamp create_at;
    private int quantity;
    private Date date;
}
