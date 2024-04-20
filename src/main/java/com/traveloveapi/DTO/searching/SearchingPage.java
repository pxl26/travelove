package com.traveloveapi.DTO.searching;

import com.traveloveapi.DTO.service.ServiceCard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchingPage {
    private List<ServiceCard> data;
    private int page;
    private int size;
    private int total_pages;
}
