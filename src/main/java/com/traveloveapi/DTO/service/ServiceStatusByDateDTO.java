package com.traveloveapi.DTO.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceStatusByDateDTO {
    private boolean isAvailable;
    private int remain;
    private String cause;
    private Date date;
}
