package com.company.domain;

import java.util.Objects;

/**
 * Defineste suma de bani detinuta de un client intr-o anumita valuta
 */

public class Money extends Currency implements Comparable<Money>{

    private int amount;

    public Money(Currency currency, int amount){
        super(currency.currencyName, currency.symbol, currency.country);
        this.amount = amount;
    }

    Money(String name, String symbol){
        super(name, symbol);
        amount = 0;
    }

    Money(String name, String symbol, String country) {
        super(name, symbol, country);
        amount = 0;
    }

    Money(String name, String symbol, int amount){
        this(name, symbol);
        this.amount = amount;
    }

    Money(String name, String symbol, String country, int amount){
        this(name, symbol, country);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean equalsCurrency(Currency currency){
        return super.equals(currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Money money = (Money) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
    }


    @Override
    public int compareTo(Money o) {
        if(this.getAmount() > o.getAmount())
            return 1;
        else if(this.getAmount() == o.getAmount()) return 0;
        return -1;
    }
}
