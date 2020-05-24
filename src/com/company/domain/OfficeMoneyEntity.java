package com.company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "office_money", schema = "pao")
public class OfficeMoneyEntity {
    private int id;
    private Double amount;
    //private Integer currencyId;
    //private int clientId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="currency_id")
    private CurrencyEntity currency;

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    @OneToOne
    @JoinColumn(name = "office_id", nullable = false)
    private OfficeEntity office;

    public OfficeEntity getOffice() {
        return office;
    }

    public void setOffice(OfficeEntity office) {
        this.office = office;
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
        OfficeMoneyEntity that = (OfficeMoneyEntity) o;
        return id == that.id &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "OfficeMoneyEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency=" + currency +
                ", client=" + office +
                '}';
    }
}
