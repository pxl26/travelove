package com.traveloveapi.DTO.service_package.bill;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import com.traveloveapi.entity.service_package.bill.voucher.BillVoucherEntity;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class BillDetailDTO {
    private String bill_id;
    private String user_id;
    private Date date;
    private Timestamp create_at;
    private Timestamp update_at;
    private Double total;
    private int quantity;
    private BillStatus status;

    private String tour_id;
    private String tour_title;
    private String tour_thumbnail;
    private float tour_rating;
    private int tour_sold;
    private String originCurrency;
    private String userCurrency;

    private ArrayList<CreateBillPersonType> person_type;
    private ArrayList<BillOption> option;

    private String feedback_id;
    private String gateway_url;
    private PayMethod pay_method;
    private String user_avatar;
    private String user_full_name;

    private ArrayList<BillVoucherEntity> voucher;
}
