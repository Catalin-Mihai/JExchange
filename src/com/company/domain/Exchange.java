package com.company.domain;

public class Exchange {

    /**
     * Defineste un transfer
     */

    private static int availableID = 0;

    private int exchangeID;
    private Client client;
    private Office office;
    private Money moneyGiven;
    private Money moneyReceived;
    private Float exchangeRate;

    public Exchange(Client client, Office office, Money moneyGiven, Money moneyReceived, Float exchangeRate) {
        this.exchangeID = availableID++;
        this.client = client;
        this.office = office;
        this.moneyGiven = moneyGiven;
        this.moneyReceived = moneyReceived;
        this.exchangeRate = exchangeRate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Money getMoneyGiven() {
        return moneyGiven;
    }

    public void setMoneyGiven(Money moneyGiven) {
        this.moneyGiven = moneyGiven;
    }

    public Money getMoneyReceived() {
        return moneyReceived;
    }

    public void setMoneyReceived(Money moneyReceived) {
        this.moneyReceived = moneyReceived;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(Integer exchangeID) {
        this.exchangeID = exchangeID;
    }
}
