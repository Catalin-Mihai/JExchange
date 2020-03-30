package com.company.view;

import com.company.domain.Client;
import com.company.domain.Currency;
import com.company.domain.ExchangeRate;
import com.company.domain.Money;
import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.MoneyRepository;
import com.company.service.*;

import javax.naming.InvalidNameException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {

    private static IOMenu ioMenu = new IOMenu(System.in, System.out);

    public static void main(String[] args) {


        /*
        Pentru o testare mai usoara
        Se pot sterge fara nicio problema

        TODO: Serviciul de tranzactii/schimb valutar.
        */

        /*-------------- DEBUG ----------------*/

        CurrencyService currencyService = new CurrencyService();
        try {
            currencyService.addCurrency("Leu", "RON", "Romania");
            currencyService.addCurrency("Dolar", "USD", "America");
            currencyService.addCurrency("Euro", "EUR", "Europa");
        } catch (DuplicateCurrencyException e) {
            e.printStackTrace();
        }

        ClientsManager clientsManager = new ClientsManager();
        try {
            clientsManager.addClient("Catalin", "Mihai");
            clientsManager.addClient("Andrei", "George");
            clientsManager.addMoney("Catalin", "Leu", 500);
            clientsManager.addMoney("Catalin", "Dolar", 400);
            clientsManager.addMoney("Andrei", "Euro", 1000);
            clientsManager.addMoney("Andrei", "Dolar", 300);
        } catch (DuplicateClientException | InvalidCurrencyNameException | DuplicateCurrencyException e) {
            e.printStackTrace();
        }

        ExchangeOfficeService exchangeOfficeService = new ExchangeOfficeService();
        try {
            exchangeOfficeService.addMoney("Euro", 50000);
            exchangeOfficeService.addMoney("Lei", 2000);
        } catch (InvalidCurrencyNameException | DuplicateCurrencyException e) {
            e.printStackTrace();
        }

        /*-------------------------------------*/

        ioMenu.showAppDesc();
        while(true){
            ioMenu.showMenu();
            try {
                ioMenu.readMenuOption();
                ioMenu.showSubMenu();
                try {
                    ioMenu.readSubMenuOption();
                    System.out.println("Going back...");
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

        }
    }
}
