package com.traveloveapi.DTO.service;

import com.traveloveapi.constrain.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourDTO {
    private String title;
    private String highlight;
    private String note;
    private ServiceType service_type;
    private String description;
}
