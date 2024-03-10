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

    private float discount_amount;
}
