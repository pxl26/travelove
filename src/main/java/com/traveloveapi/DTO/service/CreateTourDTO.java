package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.Currency;
import com.traveloveapi.constrain.Language;
import com.traveloveapi.constrain.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourDTO {
    private ServiceType service_type;
    private String title;
    private String highlight;
    private String note;
    private String description;
    private Currency currency;
    private Language pimary_language;
    private String city_id;
}
