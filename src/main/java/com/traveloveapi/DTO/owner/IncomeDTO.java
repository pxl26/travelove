package com.traveloveapi.DTO.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
    private Double total;
    private String owner_id;
    private Date from;
    private Date to;
    private String tour_id;
    private Long total_bill;
}
