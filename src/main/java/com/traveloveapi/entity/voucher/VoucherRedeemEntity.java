package com.traveloveapi.entity.voucher;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "voucher_redeem")
public class VoucherRedeemEntity {
    @Id
    private String id;

    private String key;

    private String user_id;

    private String voucher_id;

    private Timestamp redeem_at;

    private Timestamp expire_at;
}
