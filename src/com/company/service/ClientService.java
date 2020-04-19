package com.company.service;

import com.company.domain.Client;
import com.company.domain.Money;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.ClientsRepository;
import com.company.persistence.MoneyRepository;
import com.company.util.ClientData;

class ClientService extends MoneyService {

    /**
     * Folosita intern -> Constructor accesibil doar pentru acest package.
     */

    private Client client;

    ClientService(Client client) {
        this.client = client;
    }

    @Override
    protected MoneyRepository getMoneyRepository() {
        ClientData clientData = new ClientsRepository().get(client);
        return clientData.getMoneyRepository();
    }

    public void addMoney(String currencyName, Float amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        super.addMoney(currencyName, amount);
    }

    public void decreaseMoney(String currencyName, Float amount) throws InvalidCurrencyNameException {
        super.decreaseMoney(currencyName, amount);
    }

    public Float getMoneyAmount(String currencyName) {
        return super.getMoneyAmount(currencyName);
    }

    public Money getMoney(String currencyName) {
        return super.getMoney(currencyName);
    }

    public void increaseMoney(String currencyName, Float amount) throws InvalidCurrencyNameException {
        super.increaseMoney(currencyName, amount);
    }

    public void resetMoney(String currencyName) throws InvalidCurrencyNameException {
        super.resetMoney(currencyName);
    }

    public void resetAllMoney(String currencyName) throws InvalidCurrencyNameException {
        super.resetAllMoney();
    }

    public void showClientMoney(boolean sorted) {
        System.out.println("Banii clientului " + client.getName() + ": ");
        System.out.print(getFormatedMoneyAmounts(sorted));
    }

    public String getBiggestMoneyAmountFormatted() {
        return "Moneda: " + getBiggestMoneyAmount().getCurrencyName() +
                " Cantitate: " + getBiggestMoneyAmount().getAmount();
    }

    public String getSmallestMoneyAmountFormatted() {
        return "Moneda: " + getSmallestMoneyAmount().getCurrencyName() +
                " Cantitate: " + getSmallestMoneyAmount().getAmount();
    }

    public String getClientInfoFormatted() {
        return "Prenume: " + client.getFirstName() + " Nume: " + client.getLastName() +
                "\nBani: \n" + getFormatedMoneyAmounts(true);
    }
}
