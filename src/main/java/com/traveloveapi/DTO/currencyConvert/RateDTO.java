package com.traveloveapi.DTO.currencyConvert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    private String currency_name;
    private String rate;
    private String rate_for_amount;
}
