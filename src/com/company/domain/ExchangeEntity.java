package com.company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchanges", schema = "pao")
public class ExchangeEntity {
    private int id;

    @OneToOne
    @JoinColumn(name="client_id")
    private ClientEntity client;

    @OneToOne
    @JoinColumn(name="money_given_id")
    private ExchangedMoneyEntity moneyGiven;

    @OneToOne
    @JoinColumn(name="money_received_id")
    private ExchangedMoneyEntity moneyReceived;

    @OneToOne
    @JoinColumn(name="office_id")
    private OfficeEntity office;

    @OneToOne
    @JoinColumn(name="exchange_rate_id")
    private ExchangeRateEntity exchangeRate;

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ExchangedMoneyEntity getMoneyGiven() {
        return moneyGiven;
    }

    public void setMoneyGiven(ExchangedMoneyEntity moneyGiven) {
        this.moneyGiven = moneyGiven;
    }

    public ExchangedMoneyEntity getMoneyReceived() {
        return moneyReceived;
    }

    public void setMoneyReceived(ExchangedMoneyEntity moneyReceived) {
        this.moneyReceived = moneyReceived;
    }

    public OfficeEntity getOffice() {
        return office;
    }

    public void setOffice(OfficeEntity office) {
        this.office = office;
    }

    public ExchangeRateEntity getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRateEntity exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeEntity that = (ExchangeEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ExchangeEntity{" +
                "id=" + id +
                ", client=" + client + System.lineSeparator() +
                ", moneyGiven=" + moneyGiven + System.lineSeparator() +
                ", moneyReceived=" + moneyReceived + System.lineSeparator() +
                ", office=" + office + System.lineSeparator() +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
