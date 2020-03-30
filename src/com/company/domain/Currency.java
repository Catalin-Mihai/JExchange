package com.company.domain;

import java.util.Objects;

/**
 * Defineste tipurile de valuta existente pe piata
 */

public class Currency{

//    /**
//     * ID-ul asociat valutei.
//     * Pot exista 2 sau mai multe obiecte de tip Currency cu acelasi ID
//     * Obiectele cu acelasi ID se refera la aceasi valuta
//     */
//    protected int currencyID;

    protected String currencyName;
    protected String symbol;
    protected String country;

//    /**
//     * Coeficientul cursul de schimb valutar in raport cu fiecare moneda.
//     */
//    private HashMap<Currency, Float> exchangeRate;

    Currency(String name, String symbol){
        this.currencyName = name;
        this.symbol = symbol;
        this.country = "Unknown";
        //O adaugam in managerul de valute
//        currencyID = CurrencyManager.getInstance().addCurrency(this);
    }

    Currency(String name, String symbol, String country){
        this(name, symbol);
        this.country = country;
    }

    /*Currency(String name, String symbol, String country, HashMap<Currency, Float> exchangeRate){
        this(name, symbol, country);
        this.exchangeRate = exchangeRate;
    }*/

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

//    public int getCurrencyID() {
//        return currencyID;
//    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null)  return false;
        if(o instanceof Currency){
            Currency currency = (Currency) o;
            return currencyName.equals(currency.currencyName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyName);
    }

    //
//    public HashMap<Currency, Float> getExchangeRate() {
//        return exchangeRate;
//    }
//
//    public void setExchangeRate(HashMap<Currency, Float> exchangeRate) {
//        this.exchangeRate = exchangeRate;
//    }
}
