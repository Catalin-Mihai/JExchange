package com.company.service;

import com.company.domain.Currency;
import com.company.domain.CurrencyFactory;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.CurrencyRepository;

import javax.naming.InvalidNameException;

public class CurrencyService {

    /**
     * Exista un singur repository de valute
     */
    private static CurrencyRepository currencyRepository = new CurrencyRepository();

    public Currency getCurrencyByName(String name){
        return currencyRepository.getCurrencyByName(name);
    }

    public void addCurrency(String name, String symbol) throws DuplicateCurrencyException {
        currencyRepository.add(CurrencyFactory.getCurrency(name, symbol));
    }

    public void addCurrency(String name, String symbol, String country) throws DuplicateCurrencyException {
        currencyRepository.add(CurrencyFactory.getCurrency(name, symbol, country));
    }

    public void addCurrency(Currency currency){
        currencyRepository.add(currency);
    }

    public boolean exists(String currencyName){
        return currencyRepository.exists(getCurrencyByName(currencyName));
    }

    public void showAllCurrenciesInfo(){
        System.out.println("Valutele disponibile: ");
        for(Currency currency: currencyRepository.getRepository()){
            System.out.println("Nume moneda: " + currency.getCurrencyName() +
                    " - Simbol moneda: " + currency.getSymbol() +
                    " - Tara: " + currency.getCountry());
        }
    }

    public void showCurrencyInfo(String name) throws InvalidCurrencyNameException {
        Currency currency = getCurrencyByName(name);
        if(currency == null){
            throw new InvalidCurrencyNameException("Nu exista aceasta moneda!");
        }
        System.out.println("Moneda: " + currency.getCurrencyName() + " - Simbol: " + currency.getSymbol() +
                " - Tara de origine: " + currency.getCountry());
    }

    public void changeCurrencyName(String from, String to) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(from);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(to, currency.getSymbol(), currency.getCountry());
        currencyRepository.add(newCurrency);
    }

    public void changeCurrencySymbol(String name, String newSymbolName) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(name);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(currency.getCurrencyName(), newSymbolName, currency.getCountry());
        currencyRepository.add(newCurrency);
    }

    public void changeCurrencyCountry(String name, String newCountryName) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(name);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(currency.getCurrencyName(), currency.getSymbol(), newCountryName);
        currencyRepository.add(newCurrency);
    }

    public void removeCurrency(String name){
        currencyRepository.delete(currencyRepository.getCurrencyByName(name));
    }
}
