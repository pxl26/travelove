package com.traveloveapi.entity.service_package.bill.voucher;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "bill_voucher")
@IdClass(BillVoucherID.class)
public class BillVoucherEntity {
    @Id
    private String bill_id;

    @Id
    private String voucher_id;

    private Double discount_amount;

    public BillVoucherEntity(String bill_id, String voucher_id, Double discount_amount) {
        this.bill_id = bill_id;
        this.voucher_id = voucher_id;
        this.discount_amount = discount_amount;
    }

    public BillVoucherEntity() {

    }
}
