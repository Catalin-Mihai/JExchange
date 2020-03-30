package com.company.persistence;

import com.company.domain.ExchangeRate;

import java.util.ArrayList;

public class ExchangeRatesRepository implements GenericCurrencyRepository<ExchangeRate> {

    ArrayList<ExchangeRate> exchangeRates;

    @Override
    public void add(ExchangeRate entity) {
        if(!exchangeRates.contains(entity))
            exchangeRates.add(entity);
    }

    @Override
    public ExchangeRate get(int index) throws IndexOutOfBoundsException{
        return exchangeRates.get(index);
    }

    @Override
    public int update(ExchangeRate entity) {
        for(int i = 0; i < exchangeRates.size(); i++){
            if(exchangeRates.get(i).getFrom() == entity.getFrom() &&
            exchangeRates.get(i).getTo() == entity.getTo()){
                exchangeRates.set(i, entity);
                return 1;
            }
        }
        return 0;
    }

    //Nu avem duplicate
    @Override
    public void delete(ExchangeRate entity) {
        exchangeRates.remove(entity);
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void modifyRate(int index, Float rate){
        exchangeRates.get(index).setRate(rate);
    }
}
