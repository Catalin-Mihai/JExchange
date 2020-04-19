package com.company.persistence;

import com.company.domain.Currency;

import java.util.HashSet;

public class CurrencyRepository {

    private HashSet<Currency> currencies;

    public CurrencyRepository() {
        currencies = new HashSet<>();
    }

    public boolean exists(Currency currency) {
        return currencies.contains(currency);
    }

    public Currency getCurrencyByName(String name) {
        for (Currency curr : currencies) {
            // Valuta a fost definita
            if (curr.getCurrencyName().equals(name)) {
                return curr;
            }
        }
        return null;
    }

    public HashSet<Currency> getAllCurrencies() {
        return currencies;
    }

    public void add(Currency entity) {
        currencies.add(entity);
    }

    public void update(Currency entity) {
        for (Currency curr : currencies) {
            if (curr == entity) {
                currencies.remove(curr);
                currencies.add(entity);
            }
        }
    }

    public void delete(Currency entity) {
        currencies.remove(entity);
    }

    public HashSet<Currency> getRepository() {
        return currencies;
    }

    public int getSize() {
        return currencies.size();
    }
}
