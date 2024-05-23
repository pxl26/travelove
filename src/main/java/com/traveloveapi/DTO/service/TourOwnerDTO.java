package com.traveloveapi.DTO.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourOwnerDTO{
    @Id
    private String id;
    private String name;
    private String avatar;
    private Double rating;
    private Long sold;
}
