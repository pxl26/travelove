package com.traveloveapi.entity.join_entity;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class JoinBillRow {
    private String bill_id;
    private Double total;
    private Timestamp create_at;
    private Timestamp update_at;
    private Date date;
    private BillStatus status;
    private int quantity;

    private String person_type;
    private int person_quantity;

    private String group_name;
    private String option_name;

    private String tour_name;
    private String tour_id;
    private String tour_thumbnail;
    private float rating;
    private int sold;
    private String currency;

    private String user_id;

    private String feedback_id;
    private String gateway_url;
    private PayMethod pay_method;
    private String user_avatar;
    private String user_full_name;

    private String voucher_id;
    private Double voucher_discount_amount;

    private String bill_currency;
}
