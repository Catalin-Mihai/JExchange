package com.company.service.entity;

import com.company.domain.ClientEntity;
import com.company.domain.ClientMoneyEntity;
import com.company.domain.CurrencyEntity;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.remote.ClientMoneyRepository;
import com.company.persistence.remote.CurrencyRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

class ClientService {

    private static ClientEntity client;
    private final ClientMoneyRepository clientMoneyRepository = ClientMoneyRepository.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private static ClientService instance = null;

    public static ClientService getInstance(ClientEntity client){
        if(instance == null) {
            instance = new ClientService();
        }
        ClientService.client = client;
        return instance;
    }

    private ClientService() {
    }

    CurrencyEntity getByCurrencyName(String currencyName) {
        return CurrencyRepository.getInstance().getCurrency(currencyName);
    }

    public void addMoney(String currencyName, Double amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        if (currencyService.getCurrencyByName(currencyName) == null)
            throw new InvalidCurrencyNameException("Nu exista aceasta valuta");
        if (getMoney(currencyName) != null)
            throw new DuplicateCurrencyException("Exista deja bani de aceasta valuta");

        ClientMoneyEntity clientMoneyEntity = new ClientMoneyEntity();
        clientMoneyEntity.setClient(client);
        clientMoneyEntity.setAmount(amount);
        clientMoneyEntity.setCurrency(getByCurrencyName(currencyName));
        clientMoneyRepository.add(clientMoneyEntity);
    }

    public void decreaseMoney(String currencyName, Double amount) throws InvalidCurrencyNameException {
        if (amount < 0) {
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        ClientMoneyEntity money = clientMoneyRepository.getMoney(client, getByCurrencyName(currencyName));

        if (money == null)
            throw new DuplicateCurrencyException("Nu exista bani de aceasta valuta");

        if (money.getAmount() < amount)
            throw new InvalidParameterException("Nu se poate efectua operatiunea!");

        money.setAmount(money.getAmount() - amount);
        clientMoneyRepository.update(money);
    }

    public Double getMoneyAmount(String currencyName) {
        return clientMoneyRepository.getMoney(client, getByCurrencyName(currencyName)).getAmount();
    }

    public ClientMoneyEntity getMoney(String currencyName) {
        return clientMoneyRepository.getMoney(client, getByCurrencyName(currencyName));
    }

    public void increaseMoney(String currencyName, Double amount) throws InvalidCurrencyNameException {
        if (amount < 0) {
            throw new InvalidParameterException("Amount nu poate fi negativ!");
        }

        ClientMoneyEntity money = clientMoneyRepository.getMoney(client, getByCurrencyName(currencyName));

        if (money == null)
            throw new InvalidCurrencyException("Nu exista bani de aceasta valuta");

        money.setAmount(money.getAmount() + amount);
        clientMoneyRepository.update(money);
    }

    public void resetMoney(String currencyName) throws InvalidCurrencyNameException {

        ClientMoneyEntity money = clientMoneyRepository.getMoney(client, getByCurrencyName(currencyName));
        if (money == null)
            throw new DuplicateCurrencyException("Nu exista bani de aceasta valuta");

        money.setAmount(0d);
        clientMoneyRepository.update(money);
    }

    public void resetAllMoney() throws InvalidCurrencyNameException {
        clientMoneyRepository.resetAllMoney(client);
    }

    public void showClientMoney(boolean sorted) {
        System.out.println("Banii clientului " + client.getName() + ": ");
        System.out.print(getFormatedMoneyAmounts(sorted));
    }

    public ClientMoneyEntity getBiggestMoneyAmount(ClientEntity ofEntity) {
        return clientMoneyRepository.getAllMoneySorted(ofEntity, false).get(0);
    }

    public ClientMoneyEntity getSmallestMoneyAmount(ClientEntity ofEntity) {
        return clientMoneyRepository.getAllMoneySorted(ofEntity, true).get(0);
    }

    public String getBiggestMoneyAmountFormatted() {
        return "Moneda: " + getBiggestMoneyAmount(client).getCurrency().getName() +
                " Cantitate: " + getBiggestMoneyAmount(client).getAmount();
    }

    public String getSmallestMoneyAmountFormatted() {
        return "Moneda: " + getSmallestMoneyAmount(client).getCurrency().getName() +
                " Cantitate: " + getSmallestMoneyAmount(client).getAmount();
    }

    public List<ClientMoneyEntity> getAllMoney() {
        return clientMoneyRepository.getAllMoney(client);
    }

    public String getFormatedMoneyAmounts(boolean sorted) {
        ArrayList<ClientMoneyEntity> allMoney;
        if (sorted)
            allMoney = (ArrayList<ClientMoneyEntity>) clientMoneyRepository.getAllMoneySorted(client, true);
        else
            allMoney = (ArrayList<ClientMoneyEntity>) clientMoneyRepository.getAllMoney(client);

        if (allMoney == null)
            return "-";
        StringBuilder string = new StringBuilder();
        for (ClientMoneyEntity money : allMoney) {
            string.append(money.getCurrency().getName()).append(" : ").append(money.getAmount()).append(System.lineSeparator());
        }
        return string.toString();
    }

    public String getClientInfoFormatted() {
        return "Prenume: " + client.getFirstName() + " Nume: " + client.getLastName() +
                "\nBani: \n" + getFormatedMoneyAmounts(true);
    }
}
