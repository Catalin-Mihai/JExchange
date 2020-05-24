package com.company.persistence.local;

import com.company.domain.CurrencyEntity;
import com.company.persistence.GenericRepositoryInterface;

import java.util.Collection;
import java.util.HashSet;

public class CurrencyRepository implements GenericRepositoryInterface<CurrencyEntity> {

    private static CurrencyRepository instance = null;

    public static CurrencyRepository getInstance() {
        if(instance == null)
            instance = new CurrencyRepository();
        return instance;
    }

    private CurrencyRepository() {}

    private static final HashSet<CurrencyEntity> currencies = new HashSet<>();

    public boolean exists(CurrencyEntity currency) {
        return currencies.contains(currency);
    }

    public CurrencyEntity getCurrency(String name) {
        for (CurrencyEntity curr : currencies) {
            // Valuta a fost definita
            if (curr.getName().equals(name)) {
                return curr;
            }
        }
        return null;
    }

    public Collection<CurrencyEntity> getAll() {
        return currencies;
    }

    public void add(CurrencyEntity entity) {
        currencies.add(entity);
    }

    @Override
    public CurrencyEntity get(int id) {
        for (CurrencyEntity curr : currencies) {
            // Valuta a fost definita
            if (curr.getId() == id) {
                return curr;
            }
        }
        return null;
    }

    public void update(CurrencyEntity entity) {
        for (CurrencyEntity curr : currencies) {
            if (curr.getId() == entity.getId()) {
                currencies.remove(curr);
                currencies.add(entity);
            }
        }
    }

    public void delete(CurrencyEntity entity) {
        currencies.remove(entity);
    }

    public int getSize() {
        return currencies.size();
    }
}
