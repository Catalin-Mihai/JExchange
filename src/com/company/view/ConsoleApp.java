package com.company.view;

import com.company.service.entity.LogService;

import java.security.InvalidAlgorithmParameterException;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {

    private static final String clientsFileName = "./database/clients.csv";
    private static final String currenciesFileName = "./database/currencies.csv";
    private static final String exchangeRatesFileName = "./database/exchange_rates.csv";
    private static final String exchangesFileName = "./database/exchanges.csv";
    private static final String exchangeOfficeFileName = "./database/office.csv";
    private static final String logsFileName = "./database/logs.csv";
    private static final IOMenu ioMenu = new IOMenu(System.in, System.out);

    public static void main(String[] args) {

        LogService.getInstance().setLogFileName(logsFileName);

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
    }
}