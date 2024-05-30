package com.traveloveapi.service.currency;

import com.traveloveapi.entity.CurrencyEntity;
import com.traveloveapi.exception.CustomException;
import com.traveloveapi.repository.CurrencyConversionRepository;
import com.traveloveapi.service.redis.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyService {
    final private CurrencyConversionRepository currencyRepository;
    final private RedisService redisService;

    public Double convert(String from, String to, Double amount) {
        if (to==null)
            return amount;
        Double fromRate = getRate(from);
        Double toRate = getRate(to);
        return fromRate/toRate*amount;
    }

    Double getRate(String currency) {
        String value = redisService.getConnection().get("currency:"+currency);
        if (value != null) {
            return Double.parseDouble(value);
        }
        CurrencyEntity currencyEntity = currencyRepository.getCurrencyConversion(currency);
        if (currencyEntity == null) {
            throw new CustomException("Currency is not supported", 400);
        }
        redisService.getConnection().set("currency:"+currency,String.valueOf(currencyEntity.getAmount()));
        return currencyEntity.getAmount();
    }
}
