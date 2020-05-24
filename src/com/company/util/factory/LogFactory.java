package com.company.util.factory;

import com.company.domain.*;
import com.company.service.entity.ClientsManager;
import com.company.service.entity.CurrencyService;
import com.company.service.entity.LogService;
import com.company.service.entity.OfficeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFactory {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static LogEntity getLog(LogService.LogTypes logType, String... param) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LogEntity log = new LogEntity();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.toString());
        LogEntity.LogLevel logLevel;
        String message;
        ClientMoneyEntity clientMoney = new ClientMoneyEntity();
        OfficeMoneyEntity officeMoney = new OfficeMoneyEntity();
        ClientEntity client;

        switch (logType) {

        /*
            CLIENT
         */
            case CLIENT_ADD_CLIENT:
                logLevel = LogEntity.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "A fost adaugat clientul " + client.getName();
                break;
            case CLIENT_CHANGE_LASTNAME:
            case CLIENT_CHANGE_FIRSTNAME:
                logLevel = LogEntity.LogLevel.LOW;
                message = "Numele clientului " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CLIENT_RESET_ALL_MONEY:
                logLevel = LogEntity.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in toate valutele.";
                break;
            case CLIENT_RESET_MONEY:
                logLevel = LogEntity.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in valuta "
                        + param[1];
                break;
            case CLIENT_ADD_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                clientMoney.setCurrency(new CurrencyService().getCurrencyByName(param[1]));
                clientMoney.setAmount(Double.parseDouble(param[2]));
                clientMoney.setClient(client);
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati bani de o noua valuta: "
                        + clientMoney.toString();
                break;
            case CLIENT_INCREASE_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                clientMoney.setCurrency(new CurrencyService().getCurrencyByName(param[1]));
                clientMoney.setAmount(Double.parseDouble(param[2]));
                clientMoney.setClient(client);
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati "
                        + clientMoney.toString();
                break;
            case CLIENT_DECREASE_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                clientMoney.setCurrency(new CurrencyService().getCurrencyByName(param[1]));
                clientMoney.setAmount(Double.parseDouble(param[2]));
                clientMoney.setClient(client);
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost scazuti "
                        + clientMoney.toString();
                break;

        /*
            CURRENCY
         */

            case CURRENCY_ADD_CURRENCY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                CurrencyEntity currency = new CurrencyService().getCurrencyByName(param[0]);
                message = "A fost adaugata moneda -> Nume: " + param[0]
                        + " Simbol: " + currency.getSymbol() + " Tara: " + currency.getCountry();
                break;
            case CURRENCY_DELETE_CURRENCY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                message = "A fost stearsa moneda " + param[0];
                break;
            case CURRENCY_CHANGE_CURRENCY_NAME:
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Numele monedei " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CURRENCY_CHANGE_CURRENCY_COUNTRY:
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Numele tarii de provenienta a monedei " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CURRENCY_CHANGE_CURRENCY_SYMBOL:
                logLevel = LogEntity.LogLevel.HIGH;
                message = "Simbolul monedei " + param[0] + " a fost schimbat in " + param[1];
                break;

        /*
            OFFICE
         */

            case OFFICE_ADD_MONEY:
            case OFFICE_INCREASE_MONEY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                officeMoney.setOffice(new OfficeService().getOffice(param[0]));
                officeMoney.setCurrency(new CurrencyService().getCurrencyByName(param[1]));
                officeMoney.setAmount(Double.parseDouble(param[2]));
                message = "Au fost adaugati " + officeMoney.getCurrency().getName() + " " + officeMoney.getAmount()
                        + " casei de schimb valutar " + officeMoney.getOffice().getName();
                break;
            case OFFICE_DECREASE_MONEY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                officeMoney.setOffice(new OfficeService().getOffice(param[0]));
                officeMoney.setCurrency(new CurrencyService().getCurrencyByName(param[1]));
                officeMoney.setAmount(Double.parseDouble(param[2]));
                message = "Au fost retrasi " + officeMoney.getCurrency().getName() + " " + officeMoney.getAmount()
                        + " casei de schimb valutar " + officeMoney.getOffice().getName();
                break;
            case OFFICE_RESET_MONEY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                message = "Banii casei de schimb valutar " + param[0] + "au fost resetati.";
                break;


        /*
            EXCHANGE
         */

            case EXCHANGE_EXCHANGE_MONEY:
                logLevel = LogEntity.LogLevel.MEDIUM;
                ExchangeEntity exchange = new OfficeService().getExchange(Integer.parseInt(param[0]));
                message = "Clientul " + exchange.getClient().getName()
                        + " a schimbat suma de "
                        + exchange.getMoneyGiven()
                        + " in "
                        + exchange.getMoneyReceived()
                        + " la rata de schimb "
                        + exchange.getExchangeRate();
                break;
            case EXCHANGE_ADD_EXCHANGE_RATE:
                logLevel = LogEntity.LogLevel.HIGH;
                message = "A fost adaugata o noua conversie valutara: " + param[0]
                        + " -> " + param[1] + " la rata de: "
                        + param[2];
                break;
            default:
                throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }
}
