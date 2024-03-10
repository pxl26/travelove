package com.traveloveapi.entity.voucher;

import com.traveloveapi.constrain.voucher.VoucherRedeemStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "voucher_redeem")
public class VoucherRedeemEntity {
    @Id
    private String id;

    private String redeem_key;

    private String user_id;

    private String voucher_id;

    private Timestamp redeem_at;

    private Timestamp expire_at;

    @Enumerated(EnumType.STRING)
    private VoucherRedeemStatus status;
}
