package com.traveloveapi.entity.service_package.bill;

import com.traveloveapi.constrain.BillStatus;
import jakarta.persistence.*;
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

    private Timestamp update_at;

    @Enumerated(EnumType.STRING)
    private BillStatus status;
}
