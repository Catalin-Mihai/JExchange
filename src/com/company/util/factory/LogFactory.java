package com.company.util.factory;

import com.company.domain.*;
import com.company.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class LogFactory {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Log getLog(LogService.LogTypes logType, String... param) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.toString());
        Log.LogLevel logLevel;
        String message;
        Money money;
        Client client;

        switch (logType) {

        /*
            CLIENT
         */
            case CLIENT_ADD_CLIENT:
                logLevel = Log.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "A fost adaugat clientul " + client.getName();
                break;
            case CLIENT_CHANGE_LASTNAME:
            case CLIENT_CHANGE_FIRSTNAME:
                logLevel = Log.LogLevel.LOW;
                message = "Numele clientului " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CLIENT_RESET_ALL_MONEY:
                logLevel = Log.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in toate valutele.";
                break;
            case CLIENT_RESET_MONEY:
                logLevel = Log.LogLevel.HIGH;
                client = new ClientsManager().getClientByFirstName(param[0]);
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in valuta "
                        + param[1];
                break;
            case CLIENT_ADD_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                money = new Money(new CurrencyService().getCurrencyByName(param[1]), Float.parseFloat(param[2]));
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati bani de o noua valuta: "
                        + money.toString();
                break;
            case CLIENT_INCREASE_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                money = new Money(new CurrencyService().getCurrencyByName(param[1]), Float.parseFloat(param[2]));
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati "
                        + money.toString();
                break;
            case CLIENT_DECREASE_MONEY:
                client = new ClientsManager().getClientByFirstName(param[0]);
                money = new Money(new CurrencyService().getCurrencyByName(param[1]), Float.parseFloat(param[2]));
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost scazuti "
                        + money.toString();
                break;

        /*
            CURRENCY
         */

            case CURRENCY_ADD_CURRENCY:
                logLevel = Log.LogLevel.MEDIUM;
                Currency currency = new CurrencyService().getCurrencyByName(param[0]);
                message = "A fost adaugata moneda -> Nume: " + param[0]
                        + " Simbol: " + currency.getCurrencySymbol() + " Tara: " + currency.getCountry();
                break;
            case CURRENCY_DELETE_CURRENCY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "A fost stearsa moneda " + param[0];
                break;
            case CURRENCY_CHANGE_CURRENCY_NAME:
                logLevel = Log.LogLevel.HIGH;
                message = "Numele monedei " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CURRENCY_CHANGE_CURRENCY_COUNTRY:
                logLevel = Log.LogLevel.HIGH;
                message = "Numele tarii de provenienta a monedei " + param[0] + " a fost schimbat in " + param[1];
                break;
            case CURRENCY_CHANGE_CURRENCY_SYMBOL:
                logLevel = Log.LogLevel.HIGH;
                message = "Simbolul monedei " + param[0] + " a fost schimbat in " + param[1];
                break;

        /*
            OFFICE
         */

            case OFFICE_ADD_MONEY:
            case OFFICE_INCREASE_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Au fost adaugati " + new Money(new CurrencyService().getCurrencyByName(param[0]), Float.parseFloat(param[1]))
                        + " casei de schimb valutar";
                break;
            case OFFICE_DECREASE_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Au fost retrasi " + new Money(new CurrencyService().getCurrencyByName(param[0]), Float.parseFloat(param[1]))
                        + " casei de schimb valutar";
                break;
            case OFFICE_RESET_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Banii casei de schimb valutar au fost resetati.";
                break;


        /*
            EXCHANGE
         */

            case EXCHANGE_EXCHANGE_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                Exchange exchange = new OfficeService().getExchange(Integer.parseInt(param[0]));
                message = "Clientul " + exchange.getClient().getName()
                        + " a schimbat suma de "
                        + exchange.getMoneyGiven()
                        + " in "
                        + exchange.getMoneyReceived()
                        + " la rata de schimb "
                        + exchange.getExchangeRate();
                break;
            case EXCHANGE_ADD_EXCHANGE_RATE:
                logLevel = Log.LogLevel.HIGH;
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

    /* public enum LogTypes{
        ;
        public enum Client{
            ;
            public enum Name {
                CHANGE_LASTNAME,
                CHANGE_FIRSTNAME
            }

            public enum Money {
                RESET_MONEY,
                RESET_ALL_MONEY,
                ADD_MONEY,
                DECREASE_MONEY,
                INCREASE_MONEY
            }

            public enum Creation {
                ADD_CLIENT
            }
        }

        public enum Currency {
            ;
            public enum Creation{
                ADD_CURRENCY,
                DELETE_CURRENCY
            }

            public enum Modify{
                CHANGE_CURRENCY_NAME,
                CHANGE_CURRENCY_SYMBOL,
                CHANGE_CURRENCY_COUNTRY,
            }
        }

        public enum Office{
            ADD_MONEY,
            RESET_MONEY
        }
    }

     Log pentru schimburi valutare
    /*public static Log getLog(Exchange exchange){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setLogLevel(Log.LogLevel.MEDIUM);
        log.setNumeActiune("Schimb valutar");
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        String message = exchange.getClient().getFirstName() +
                " " + exchange.getClient().getLastName() +
                " a schimbat " + exchange.getMoneyGiven().getAmount() +
                " " + exchange.getMoneyGiven().getCurrencySymbol() +
                " in " + exchange.getMoneyReceived().getAmount() +
                exchange.getMoneyReceived().getCurrencySymbol();
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Client.Money logType, String clientFirstName, String diffCurrencyName, float diffAmount){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.toString());
        Log.LogLevel logLevel;
        String message;
        Client client = new ClientsManager().getClientByFirstName(clientFirstName);
        Money moneyDiff;

        switch (logType){
            case RESET_ALL_MONEY:
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in toate valutele.";
                break;
            case RESET_MONEY:
                moneyDiff = new Money(new CurrencyService().getCurrencyByName(diffCurrencyName), diffAmount);
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost resetati banii detinuti in valuta "
                        + moneyDiff.getCurrencyName();
                break;
            case ADD_MONEY:
                moneyDiff = new Money(new CurrencyService().getCurrencyByName(diffCurrencyName), diffAmount);
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati bani de o noua valuta: "
                        + moneyDiff.toString();
                break;
            case INCREASE_MONEY:
                moneyDiff = new Money(new CurrencyService().getCurrencyByName(diffCurrencyName), diffAmount);
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost adaugati "
                        + moneyDiff.toString();
                break;
            case DECREASE_MONEY:
                moneyDiff = new Money(new CurrencyService().getCurrencyByName(diffCurrencyName), diffAmount);
                logLevel = Log.LogLevel.HIGH;
                message = "Clientului " + client.getName() + " i-au fost scazuti "
                        + moneyDiff.toString();
                break;
            default:
                throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Client.Name logType, String oldName, String newName){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.toString());
        Log.LogLevel logLevel;
        String message;

        switch (logType){
            case CHANGE_LASTNAME:
            case CHANGE_FIRSTNAME:
                logLevel = Log.LogLevel.LOW;
                message = "Clientului " + oldName +
                        " i-a fost schimbat numele in " + newName;
                break;
            default:
                throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Client.Creation logType, String newClientName){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.name());
        Log.LogLevel logLevel;
        String message;

        if (logType == LogTypes.Client.Creation.ADD_CLIENT) {
            logLevel = Log.LogLevel.MEDIUM;
            message = "A fost adaugat clientul " + newClientName;
        } else {
            throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Currency.Creation logType, String currencyName){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.name());
        Log.LogLevel logLevel;
        String message;

        switch (logType) {
            case ADD_CURRENCY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "A fost adaugata moneda " + currencyName;
                break;
            case DELETE_CURRENCY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "A fost stearsa moneda " + currencyName;
                break;
            default:
            throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Currency.Modify logType, String currencyName, String newValue){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.name());
        Log.LogLevel logLevel;
        String message;

        switch (logType) {
            case CHANGE_CURRENCY_NAME:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Numele monedei " + currencyName + " a fost schimbata in " + newValue;
                break;
            case CHANGE_CURRENCY_SYMBOL:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Simbolul monedei " + currencyName + " a fost schimbat in " + newValue;
                break;
            case CHANGE_CURRENCY_COUNTRY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Tara de provenienta a monedei " + currencyName + " a fost schimbata in " + newValue;
                break;
            default:
                throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

    public static Log getLog(LogTypes.Office logType, String... param){
        LocalDateTime localDateTime = LocalDateTime.now();
        Log log = new Log();
        log.setTimeStamp(dateTimeFormatter.format(localDateTime));
        log.setNumeActiune(logType.name());
        Log.LogLevel logLevel;
        String message;

        switch (logType) {
            case ADD_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Au fost adaugati " + new Money(new CurrencyService().getCurrencyByName(param[0]), Float.parseFloat(param[1]))
                        + " casei de schimb valutar";
                break;
            case RESET_MONEY:
                logLevel = Log.LogLevel.MEDIUM;
                message = "Banii casei de schimb valutar au fost resetati.";
                break;
            default:
                throw new IllegalStateException("Nu exista metoda de logare pentru " + logType);
        }

        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        return log;
    }

     */
