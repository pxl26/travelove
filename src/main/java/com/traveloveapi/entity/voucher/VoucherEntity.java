package com.traveloveapi.entity.voucher;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.voucher.VoucherDiscountType;
import com.traveloveapi.constrain.voucher.VoucherStatus;
import com.traveloveapi.constrain.voucher.VoucherTargetType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "voucher")
public class VoucherEntity {
    @Id
    private String id;

    private String code;

    private String creator_id;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    private String title;

    private String detail;

    private int expiration; // num of day

    private int stock;

    private Timestamp start_at;

    private Timestamp end_at;

    @Enumerated(EnumType.STRING)
    private VoucherDiscountType discount_type;

    private float fixed_discount;

    private float percent_discount;

    private float under_applied;

    private float max_discount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private VoucherTargetType target_type;

    private String target_id;
}
