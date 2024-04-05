package com.traveloveapi.entity.pay_method;

import com.traveloveapi.constrain.PayMethodType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pay_method")
public class PayMethodEntity {
    @Id
    private String id;

    private String user_id;

    private int priority;

    private PayMethodType type;

    private String name;

    private String code;
}
