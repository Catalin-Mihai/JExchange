package com.company.persistence;

import com.company.domain.Currency;
import com.company.domain.ExchangeRate;
import com.company.exceptions.InvalidExchangeRateException;

public class ExchangeRateRepository extends GenericRepository<ExchangeRate> {

    public void modifyRate(int index, Float rate) {
        repo.get(index).setRate(rate);
    }

    public ExchangeRate getExchangeRate(Currency from, Currency to) {
        boolean exchangeInverted = false;
        for (ExchangeRate exchange : repo) {
            if (exchange.getFrom() == from && exchange.getTo() == to) {
                return exchange;
            } else if (exchange.getFrom() == to && exchange.getTo() == from) {
                exchangeInverted = true;
            }
        }
        if (exchangeInverted) {
            throw new InvalidExchangeRateException("Exista doar conversia inversa, nu si conversie directa.");
        }
        throw new InvalidExchangeRateException("Nu exista un curs de schimb intre cele 2 valute");
    }
}
