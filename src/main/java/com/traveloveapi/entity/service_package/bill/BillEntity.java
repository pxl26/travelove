package com.traveloveapi.entity.service_package.bill;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
public class BillEntity {
    @Id
    private String id;

    private String service_id;

    private String user_id;

    private Double total;

    private Timestamp create_at;

    private int quantity;

    private Date date;

    private Timestamp update_at;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    private String feedback_id;

    private String gateway_url;

    private String currency;

    @Enumerated(EnumType.STRING)
    private PayMethod pay_method;
}
