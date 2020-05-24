package com.company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchanged_money", schema = "pao")
public class ExchangedMoneyEntity {
    private int id;
    private Double amount;

    @OneToOne
    @JoinColumn(name="currency_id")
    private CurrencyEntity currency;

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 0)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangedMoneyEntity that = (ExchangedMoneyEntity) o;
        return id == that.id &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency.getId());
    }

    @Override
    public String toString() {
        return amount + " " + currency.getSymbol();
    }
}
