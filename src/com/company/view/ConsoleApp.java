package com.company.view;

import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.service.ClientsManager;
import com.company.service.CurrencyService;
import com.company.service.LogService;
import com.company.service.OfficeService;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {

    private static final String clientsFileName = "./database/clients.csv";
    private static final String currenciesFileName = "./database/currencies.csv";
    private static final String exchangeRatesFileName = "./database/exchange_rates.csv";
    private static final String exchangesFileName = "./database/exchanges.csv";
    private static final String exchangeOfficeFileName = "./database/office.csv";
    private static final String logsFileName = "./database/logs.csv";
    private static IOMenu ioMenu = new IOMenu(System.in, System.out);

    public static void main(String[] args) {

        LogService.getInstance().setLogFileName(logsFileName);

        CurrencyService currencyService = new CurrencyService();
        ClientsManager clientsManager = new ClientsManager();
        OfficeService exchangeOfficeService = new OfficeService();

        try {
            currencyService.readCurrenciesFromFile(currenciesFileName);
            clientsManager.readClientsFromFile(clientsFileName);
            exchangeOfficeService.readExchangeRatesFromFile(exchangeRatesFileName);
            exchangeOfficeService.readOfficeInfoFromFile(exchangeOfficeFileName);
            exchangeOfficeService.readExchangesFromFile(exchangesFileName);
        } catch (DuplicateCurrencyException | IOException e) {
            e.printStackTrace();
        }

        /*-------------------------------------*/

        ioMenu.showAppDesc();
        while (ioMenu.getMenuOption() != 0) {
            try {
                ioMenu.showMenu();
                ioMenu.readMenuOption();
                ioMenu.showSubMenu();
                try {
                    ioMenu.readSubMenuOption();
                    System.out.println("Going back...");
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

        }

        //Salvare in fisier
        try {
            clientsManager.writeClientsToFile(clientsFileName);
            currencyService.writeToFile(currenciesFileName);
            exchangeOfficeService.writeExchangeRatesToFile(exchangeRatesFileName);
            exchangeOfficeService.writeExchangesToFile(exchangesFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
