package com.traveloveapi.DTO.currencyConvert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConvertResponse {
    private String status;
    private String updated_date;
    private String base_currency_code;
    private String base_currency_name;
    private Double amount;
    private RateWrapper rates;
}
