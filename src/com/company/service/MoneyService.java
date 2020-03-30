package com.company.service;

import com.company.domain.Currency;
import com.company.domain.Money;
import com.company.domain.Pair;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.MoneyRepository;

import javax.naming.InvalidNameException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class MoneyService{

    private CurrencyService currencyService = new CurrencyService();

    protected abstract MoneyRepository getMoneyRepository();

    public void showList(){
        for(Money money: getMoneyRepository().getRepository()){
            System.out.println(money.getAmount());
        }
    }

    public void addMoney(String currencyName, int amount) throws InvalidCurrencyNameException, DuplicateCurrencyException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if(currency != null){
            getMoneyRepository().add(new Money(currency, amount));
        }
        else throw new InvalidCurrencyNameException("Moneda invalida");
    }



    protected ArrayList<Pair<String, Integer>> getAllMoneyAmounts(boolean sorted){

        MoneyRepository moneyRepository = getMoneyRepository();
        if(sorted) Collections.sort(moneyRepository.getRepository());

        ArrayList<Pair<String, Integer>> amounts = new ArrayList<>();
        for(int i = 0; i < moneyRepository.getSize(); i++)
        {
            amounts.add(new Pair<>(moneyRepository.get(i).getCurrencyName(), moneyRepository.get(i).getAmount()));
        }
        return amounts;
    }

    protected String getFormatedMoneyAmounts(boolean sorted){
        StringBuilder rasp = new StringBuilder();
        ArrayList<Pair<String, Integer>> sumeBani = getAllMoneyAmounts(sorted);
        for(Pair<String, Integer> suma: sumeBani){
            rasp.append(suma.getKey()).append(" : ").append(suma.getValue()).append("\n");
        }
        return rasp.toString();
    }

    public int getMoneyAmount(String currencyName) throws InvalidCurrencyNameException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if(currency != null){
            return getMoneyRepository().getAmountByCurrency(currency);
        }
        else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void increaseMoney(String currencyName, int amount) throws InvalidParameterException, InvalidCurrencyNameException {

        if(amount < 0){
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        Currency currency = currencyService.getCurrencyByName(currencyName);
        if(currency != null){
                int res = getMoneyRepository().update(new Money(currency,
                        getMoneyRepository().getAmountByCurrency(currency) + amount));
                if(res == 0) throw new InvalidCurrencyNameException("Moneda nu exista!");
        }
        else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void decreaseMoney(String currencyName, int amount) throws InvalidParameterException, InvalidCurrencyNameException {

        if(amount < 0){
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        Currency currency = currencyService.getCurrencyByName(currencyName);
        if(currency != null){
            if(getMoneyRepository().getAmountByCurrency(currency) >= amount)
                getMoneyRepository().update(new Money(currency,
                        getMoneyRepository().getAmountByCurrency(currency) - amount));
            else throw new InvalidParameterException("Nu se poate scadea aceasta suma!");
        }
        else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    protected Money getBiggestMoneyAmount(){
        return Collections.max(getMoneyRepository().getRepository());
    }

    protected Money getSmallestMoneyAmount(){
        return Collections.min(getMoneyRepository().getRepository());
    }

}
