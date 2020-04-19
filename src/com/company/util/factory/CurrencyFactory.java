package com.company.util.factory;

import com.company.domain.Currency;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.service.CurrencyService;

public class CurrencyFactory {

    public static Currency getCurrency(String name, String symbol) throws DuplicateCurrencyException {
        // Nu exista deja o valuta cu acest nume
        if (new CurrencyService().getCurrencyByName(name) != null) {
            throw new DuplicateCurrencyException("E");
        }
        return new Currency(name, symbol);
    }

    public static Currency getCurrency(String name, String symbol, String country) throws DuplicateCurrencyException {
        // Nu exista deja o valuta cu acest nume
        if (new CurrencyService().getCurrencyByName(name) != null) {
            throw new DuplicateCurrencyException("Exista deja o moneda cu acest nume");
        }
        return new Currency(name, symbol, country);
    }
}
