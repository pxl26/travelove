package com.traveloveapi.entity.refund;

import com.traveloveapi.constrain.RefundStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "refund")
@Data
public class RefundEntity {
    @Id
    private String id;

    private String bill_id;

    private String reason;

    private RefundStatus status;

    private String amount;

}
