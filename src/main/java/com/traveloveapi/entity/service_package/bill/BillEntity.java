package com.traveloveapi.entity.service_package.bill;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bill")
@Data
public class BillEntity {
    @Id
    private String id;
    private String service_id;
    private String user_id;
    private int quantity;
    private float total;
}
