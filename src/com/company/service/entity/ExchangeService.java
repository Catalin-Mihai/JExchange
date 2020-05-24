package com.company.service.entity;

import com.company.domain.*;
import com.company.persistence.remote.ExchangeRepository;
import com.company.persistence.remote.ExchangedMoneyRepository;

import java.util.List;

/**
 * Pentru uz intern -> Constructor folosit pentru acest package
 */

public class ExchangeService {

    private static final ExchangeRepository exchangeRepository = ExchangeRepository.getInstance();
    private final OfficeService officeService = new OfficeService();
    private final CurrencyService currencyService = new CurrencyService();
    private final ClientsManager clientsManager = new ClientsManager();

    ExchangeService() {
    }

    public ExchangeEntity addExchange(String officeName, String clientFirstName, String fromCurrency, String toCurrency,
                                      Double moneyGiven, Double moneyReceived, ExchangeRateEntity exchangeRate) {
        ClientEntity client = clientsManager.getClientByFirstName(clientFirstName);
        OfficeEntity office = officeService.getOffice(officeName);

        ExchangedMoneyEntity from = new ExchangedMoneyEntity();
        from.setCurrency(currencyService.getCurrencyByName(fromCurrency));
        from.setAmount(moneyGiven);

        ExchangedMoneyRepository.getInstance().add(from);
        int fromId = from.getId();
        from = new ExchangedMoneyEntity();
        from.setId(fromId);
        from.setAmount(moneyGiven);
        from.setCurrency(currencyService.getCurrencyByName(fromCurrency));

        ExchangedMoneyEntity to = new ExchangedMoneyEntity();
        to.setCurrency(currencyService.getCurrencyByName(toCurrency));
        to.setAmount(moneyReceived);

        ExchangedMoneyRepository.getInstance().add(to);
        int toId = to.getId();
        to = new ExchangedMoneyEntity();
        to.setId(toId);
        to.setCurrency(currencyService.getCurrencyByName(toCurrency));
        to.setAmount(moneyReceived);


        ExchangeEntity exchange = new ExchangeEntity(); //(client, office, from, to, exchangeRate);
        exchange.setClient(client);
        exchange.setOffice(office);
        exchange.setMoneyGiven(from);
        exchange.setMoneyReceived(to);
        exchange.setExchangeRate(exchangeRate);

        exchangeRepository.add(exchange);
        return exchange;
    }

    public List<ExchangeEntity> getAllExchanges() {
        return exchangeRepository.getAll();
    }

    public ExchangeEntity getExchange(Integer exchangeID) {
        return exchangeRepository.get(exchangeID);
    }

}
