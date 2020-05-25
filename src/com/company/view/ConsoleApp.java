package com.company.view;

import com.company.service.entity.LogService;

import java.security.InvalidAlgorithmParameterException;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {

    private static final String CLIENTS_FILE_NAME = "./database/clients.csv";
    private static final String CURRENCIES_FILE_NAME = "./database/currencies.csv";
    private static final String EXCHANGE_RATES_FILE_NAME = "./database/exchange_rates.csv";
    private static final String EXCHANGES_FILE_NAME = "./database/exchanges.csv";
    private static final String EXCHANGE_OFFICE_FILE_NAME = "./database/office.csv";
    private static final String LOGS_FILE_NAME = "./database/logs.csv";
    private static final IOMenu ioMenu = new IOMenu(System.in, System.out);

    public static void main(String[] args) {

        LogService.getInstance().setLogFileName(LOGS_FILE_NAME);

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

    private ConsoleApp(){
    }
}
