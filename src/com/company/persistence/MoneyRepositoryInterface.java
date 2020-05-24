package com.company.persistence;

import com.company.domain.CurrencyEntity;

import java.util.List;


public interface MoneyRepositoryInterface<T, K> {

    List<T> getAllMoney(K ofEntity);

    List<T> getAllMoneySorted(K ofEntity, boolean asc);

    T getMoney(K ofEntity, CurrencyEntity currency);

    void resetAllMoney(K ofEntity);
}
