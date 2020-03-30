package com.company.service;

import com.company.domain.Currency;
import com.company.domain.Money;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.MoneyRepository;

import javax.naming.InvalidNameException;
import java.security.InvalidParameterException;

public class ExchangeOfficeService extends MoneyService{

    /**
     * Serviciile de management al casei de schimb impart acelasi repository de bani
     */
    private static MoneyRepository moneyRepository = new MoneyRepository();

    @Override
    protected MoneyRepository getMoneyRepository() {
        return moneyRepository;
    }

    public String getOfficeMoney(boolean sorted){
        return getFormatedMoneyAmounts(sorted);
    }

    public void resetOfficeMoney(){
        MoneyRepository repo = moneyRepository;
        repo.getRepository().clear();
    }

    public void showBiggestMoneyAmount(){
        System.out.println("Moneda in care casa de schimb are cei mai multi bani: " + getBiggestMoneyAmount().getAmount());
    }

    public void showSmallestMoneyAmount(){
        System.out.println("Moneda in care care casa de schimb are cei mai putini bani: " + getSmallestMoneyAmount().getAmount());
    }
}
