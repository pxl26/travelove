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

    private Integer expiration; // num of day

    private Integer stock;

    private Timestamp start_at;

    private Timestamp end_at;

    @Enumerated(EnumType.STRING)
    private VoucherDiscountType discount_type;

    private Float fixed_discount;

    private Float percent_discount;

    private Float minimum_spend;

    private Float max_discount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private VoucherTargetType target_type;

    private String target_id;
}
