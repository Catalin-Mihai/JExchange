package com.company.persistence.local;

import com.company.domain.CurrencyEntity;
import com.company.domain.ExchangeRateEntity;
import com.company.exceptions.InvalidExchangeRateException;

public class ExchangeRateRepository extends GenericArrayListRepository<ExchangeRateEntity> {

    public static ExchangeRateRepository instance = null;

    public static ExchangeRateRepository getInstance() {
        if(instance == null){
            instance = new ExchangeRateRepository();
        }
        return instance;
    }

    private ExchangeRateRepository() {}

    public ExchangeRateEntity getExchangeRate(CurrencyEntity from, CurrencyEntity to) {
        boolean exchangeInverted = false;
        for (ExchangeRateEntity exchange : repo) {
            if (exchange.getFromCurrency() == from && exchange.getToCurrency() == to) {
                return exchange;
            } else if (exchange.getFromCurrency() == to && exchange.getToCurrency() == from) {
                exchangeInverted = true;
            }
        }
        if (exchangeInverted) {
            throw new InvalidExchangeRateException("Exista doar conversia inversa, nu si conversie directa.");
        }
        throw new InvalidExchangeRateException("Nu exista un curs de schimb intre cele 2 valute");
    }

    @Override
    public ExchangeRateEntity get(int id) {
        for(ExchangeRateEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }
}
