package com.traveloveapi.entity.pay_method;

import com.traveloveapi.constrain.PayMethodType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pay_method")
public class PayMethodEntity {
    @Id
    private String id;

    private String user_id;

    private int priority;

    @Enumerated(EnumType.STRING)
    private PayMethodType type;

    private String name;

    private String code;

    private String account_owner;
}
