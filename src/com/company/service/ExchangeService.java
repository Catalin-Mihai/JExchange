package com.company.service;

import com.company.domain.Client;
import com.company.domain.Exchange;
import com.company.domain.Money;
import com.company.domain.Office;
import com.company.persistence.ExchangeRepository;

import java.util.ArrayList;

/**
 * Pentru uz intern -> Constructor folosit pentru acest package
 */

public class ExchangeService {

    private static ExchangeRepository exchangeRepository = new ExchangeRepository();
    private OfficeService officeService = new OfficeService();
    private CurrencyService currencyService = new CurrencyService();
    private ClientsManager clientsManager = new ClientsManager();

    ExchangeService() {
    }

    public Exchange addExchange(String clientFirstName, String fromCurrency, String toCurrency,
                                float moneyGiven, float moneyReceived, float exchangeRate) {
        Client client = clientsManager.getClientByFirstName(clientFirstName);
        Office office = officeService.getOffice();
        Money from = new Money(currencyService.getCurrencyByName(fromCurrency), moneyGiven);
        Money to = new Money(currencyService.getCurrencyByName(toCurrency), moneyReceived);
        Exchange exchange = new Exchange(client, office, from, to, exchangeRate);
        exchangeRepository.add(exchange);
        return exchange;
    }

    public ArrayList<Exchange> getAllExchanges() {
        return exchangeRepository.getRepo();
    }

    public Exchange getExchange(Integer exchangeID) {
        return exchangeRepository.getByID(exchangeID);
    }

}
