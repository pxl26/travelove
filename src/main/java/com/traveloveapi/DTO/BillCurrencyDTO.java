package com.traveloveapi.DTO;

import com.traveloveapi.constrain.BillStatus;
import com.traveloveapi.constrain.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillCurrencyDTO {
    private String id;

    private String service_id;

    private String user_id;

    private Double total;

    private Timestamp create_at;

    private int quantity;

    private Date date;

    private Timestamp update_at;

    private BillStatus status;

    private String feedback_id;

    private String gateway_url;

    private PayMethod pay_method;

    private String originCurrency;

    private String userCurrency;
}
