package com.company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchange_rates", schema = "pao")
public class ExchangeRateEntity {
    private int id;
    private Double rate;

    @OneToOne
    @JoinColumn(name="from_currency_id")
    private CurrencyEntity fromCurrency;

    @OneToOne
    @JoinColumn(name="to_currency_id")
    private CurrencyEntity toCurrency;


    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public CurrencyEntity getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(CurrencyEntity fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public CurrencyEntity getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(CurrencyEntity toCurrency) {
        this.toCurrency = toCurrency;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rate", nullable = true, precision = 0)
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateEntity that = (ExchangeRateEntity) o;
        return id == that.id &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate);
    }

    @Override
    public String toString() {
        return "ExchangeRateEntity{" +
                "id=" + id +
                ", rate=" + rate +
                '}';
    }
}
