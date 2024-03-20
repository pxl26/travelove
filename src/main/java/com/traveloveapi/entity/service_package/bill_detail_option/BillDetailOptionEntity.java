package com.traveloveapi.entity.service_package.bill_detail_option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "bill_detail_option")
@IdClass(BillDetailOptionEntity.class)
public class BillDetailOptionEntity implements Serializable {
    @Id
    private String bill_id;
    @Id
    private int group_number;
    @Id
    private int option_number;

}
