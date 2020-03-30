package com.company.service;

import com.company.domain.Client;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.MoneyRepository;

import java.util.Collections;

public class ClientService extends MoneyService{

    private Client client;

    public ClientService(Client client){
        this.client = client;
    }

    @Override
    protected MoneyRepository getMoneyRepository() {
        return client.getMoneyRepository();
    }

    public void addMoney(String currencyName, int amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        super.addMoney(currencyName, amount);
    }

    public void decreaseMoney(String currencyName, int amount) throws InvalidCurrencyNameException {
        super.decreaseMoney(currencyName, amount);
    }

    public void increaseMoney(String currencyName, int amount) throws InvalidCurrencyNameException {
        super.increaseMoney(currencyName, amount);
    }

    public void showClientMoney(boolean sorted){
        System.out.println("Banii clientului " + client.getName() + ": ");
        System.out.print(getFormatedMoneyAmounts(sorted));
    }

    public String getBiggestMoneyAmountFormatted(){
        return "Moneda: " + getBiggestMoneyAmount().getCurrencyName() +
                " Cantitate: " + getBiggestMoneyAmount().getAmount();
    }

    public String getSmallestMoneyAmountFormatted(){
        return "Moneda: " + getSmallestMoneyAmount().getCurrencyName() +
                " Cantitate: " + getSmallestMoneyAmount().getAmount();
    }

    public String getClientInfoFormatted(){
        return "Nume: " + client.getFirstName() + " Prenume: " + client.getLastName() +
                "\nBani: \n" + getFormatedMoneyAmounts(true);
    }
}
