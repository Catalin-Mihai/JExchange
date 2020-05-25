package com.company.util.factory;

import com.company.domain.CurrencyEntity;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.service.entity.CurrencyService;

public class CurrencyEntityFactory {

    private CurrencyEntityFactory(){
    }

    public static CurrencyEntity getCurrency(String name, String symbol) throws DuplicateCurrencyException {
        // Nu exista deja o valuta cu acest nume
        if (CurrencyService.getInstance().getCurrencyByName(name) != null) {
            throw new DuplicateCurrencyException("Valuta exista deja");
        }
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setName(name);
        currencyEntity.setSymbol(symbol);
        return currencyEntity;
    }

    public static CurrencyEntity getCurrency(String name, String symbol, String country) throws DuplicateCurrencyException {
        // Nu exista deja o valuta cu acest nume
        if (CurrencyService.getInstance().getCurrencyByName(name) != null) {
            throw new DuplicateCurrencyException("Valuta exista deja");
        }
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setName(name);
        currencyEntity.setSymbol(symbol);
        currencyEntity.setCountry(country);
        return currencyEntity;
    }
}
