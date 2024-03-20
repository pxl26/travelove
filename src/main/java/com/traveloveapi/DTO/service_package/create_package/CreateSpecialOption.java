package com.traveloveapi.DTO.service_package.create_package;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpecialOption {
    private int group_number;
    private int option_number;
    private boolean isDisable;
}
