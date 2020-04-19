package com.company.service;

import com.company.domain.Currency;
import com.company.domain.Money;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.MoneyRepository;
import com.company.util.Pair;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class MoneyService {

    private CurrencyService currencyService = new CurrencyService();

    public static MoneyRepository computeDiff(MoneyRepository oldRepo, MoneyRepository newRepo) {

        /*
            [CONVENTIE] Daca o diferenta are amount = 0 atunci ea a fost stearsa
            [CONVENTIE] Daca o diferenta are amount negativ, inseamna ca au fost sustrasi bani
            [CONVENTIE] Daca o diferenta are amount pozitiv, inseamna ca au fost adaugati bani
         */

        MoneyRepository diffRepo = new MoneyRepository();
        //Gaseste diferentele de valuta. Daca exista calculeaza diff de amount. Daca nu, insereaza valuta ca diferenta
        for (Currency currency : new CurrencyService().getAllCurencies()) {
            Money oldRepoMoney = oldRepo.getByCurrency(currency);
            Money newRepoMoney = newRepo.getByCurrency(currency);
            //Este in ambele
            if (oldRepoMoney != null && newRepoMoney != null) {
                //Calculeaza diff de amount
                float amountDiff = newRepoMoney.getAmount() - oldRepoMoney.getAmount();
                //Este diferenta de amount
                if (amountDiff != 0) {
                    Money diffMoney = new Money(currency, amountDiff);
                    diffRepo.add(diffMoney);
                }
            } else if (oldRepoMoney == null) {
                // A fost adaugata o valuta
                Money diffMoney = new Money(currency, newRepoMoney.getAmount());
                diffRepo.add(diffMoney);
            } else {
                //A fost stearga o valuta
                Money diffMoney = new Money(currency, 0f);
                diffRepo.add(diffMoney);
            }
        }
        return diffRepo;
    }

    protected abstract MoneyRepository getMoneyRepository();

    public void showList() {
        for (Money money : getMoneyRepository().getRepository()) {
            System.out.println(money.getAmount());
        }
    }

    public void addMoney(String currencyName, Float amount) throws InvalidCurrencyNameException, DuplicateCurrencyException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            boolean success = getMoneyRepository().add(new Money(currency, amount));
            if (!success) {
                throw new DuplicateCurrencyException("Moneda exista deja");
            }
        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    protected ArrayList<Pair<String, Float>> getAllMoneyAmounts(boolean sorted) {

        MoneyRepository moneyRepository = getMoneyRepository();
        if (sorted) Collections.sort(moneyRepository.getRepository());

        ArrayList<Pair<String, Float>> amounts = new ArrayList<>();
        for (int i = 0; i < moneyRepository.getSize(); i++) {
            amounts.add(new Pair<>(moneyRepository.get(i).getCurrencyName(), moneyRepository.get(i).getAmount()));
        }
        return amounts;
    }

    protected String getFormatedMoneyAmounts(boolean sorted) {
        StringBuilder rasp = new StringBuilder();
        ArrayList<Pair<String, Float>> sumeBani = getAllMoneyAmounts(sorted);
        for (Pair<String, Float> suma : sumeBani) {
            rasp.append(suma.getKey()).append(" : ").append(suma.getValue()).append("\n");
        }
        return rasp.toString();
    }

    public Float getMoneyAmount(String currencyName) throws InvalidCurrencyNameException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            return getMoneyRepository().getAmountByCurrency(currency);
        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public Money getMoney(String currencyName) throws InvalidCurrencyNameException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            return getMoneyRepository().getByCurrency(currencyService.getCurrencyByName(currencyName));
        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void increaseMoney(String currencyName, Float amount) throws InvalidParameterException, InvalidCurrencyNameException {

        if (amount < 0) {
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            int res = getMoneyRepository().update(new Money(currency,
                    getMoneyRepository().getAmountByCurrency(currency) + amount));
            if (res == 0) throw new InvalidCurrencyNameException("Moneda nu exista!");
        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void decreaseMoney(String currencyName, Float amount) throws InvalidParameterException, InvalidCurrencyNameException {

        if (amount < 0) {
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            if (getMoneyRepository().getAmountByCurrency(currency) >= amount)
                getMoneyRepository().update(new Money(currency,
                        getMoneyRepository().getAmountByCurrency(currency) - amount));
            else throw new InvalidParameterException("Nu se poate scadea aceasta suma!");
        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void resetMoney(String currencyName) throws InvalidCurrencyNameException {
        Currency currency = currencyService.getCurrencyByName(currencyName);
        if (currency != null) {
            Money money = getMoneyRepository().getByCurrency(currency);
            money.setAmount(0f);

        } else throw new InvalidCurrencyNameException("Moneda invalida");
    }

    public void resetAllMoney() {
        ArrayList<Money> moneyList = getMoneyRepository().getRepository();
        for (Money money : moneyList) {
            money.setAmount(0f);
        }
    }

    protected Money getBiggestMoneyAmount() {
        return Collections.max(getMoneyRepository().getRepository());
    }

    protected Money getSmallestMoneyAmount() {
        return Collections.min(getMoneyRepository().getRepository());
    }

}
