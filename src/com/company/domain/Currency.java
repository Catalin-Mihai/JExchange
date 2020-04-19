package com.company.domain;

import java.util.Objects;

/**
 * Defineste tipurile de valuta existente
 */

public class Currency {

    protected String currencyName;
    protected String symbol;
    protected String country;

    public Currency(String name, String symbol) {
        this.currencyName = name;
        this.symbol = symbol;
        this.country = "Unknown";
    }

    public Currency(String name, String symbol, String country) {
        this(name, symbol);
        this.country = country;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Currency) {
            Currency currency = (Currency) o;
            return currencyName.equals(currency.currencyName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyName);
    }

}
