package com.company.util;

import com.company.domain.Client;
import com.company.persistence.MoneyRepository;

public class ClientData {

    Pair<Client, MoneyRepository> pair;

    public ClientData(Client client, MoneyRepository moneyRepository) {
        pair = new Pair<>(client, moneyRepository);
    }

    public Client getClient() {
        return pair.getKey();
    }

    public void setClient(Client client) {
        pair.setKey(client);
    }

    public MoneyRepository getMoneyRepository() {
        return pair.getValue();
    }

    public void setMoney(MoneyRepository money) {
        pair.setValue(money);
    }

}
