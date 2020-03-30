package com.company.domain;

import java.util.Objects;

public class ExchangeRate {

    /**
     * Valutele de referinta
     */
    private Currency from;
    private Currency to;

    /**
     * Rata de schimb (Cursul valutar) pentru cele doua monezi.
     */
    private Float rate;

    ExchangeRate(Currency from, Currency to, Float rate){
        this.from = from;
        this.to = to;
        this.rate = rate;
    }


    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, rate);
    }
}
