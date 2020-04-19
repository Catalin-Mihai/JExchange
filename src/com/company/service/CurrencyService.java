package com.company.service;

import com.company.domain.Currency;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.CurrencyRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;
import com.company.util.factory.CurrencyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CurrencyService {


    private static final String exportFileHeader = "\"Currency Name\", \"Currency Symbol\", \"Currency Country\"";
    private static CurrencyRepository currencyRepository = new CurrencyRepository();

    public Currency getCurrencyByName(String name) {
        return currencyRepository.getCurrencyByName(name);
    }

    public void addCurrency(String name, String symbol) throws DuplicateCurrencyException {
        currencyRepository.add(CurrencyFactory.getCurrency(name, symbol));
    }

    public void addCurrency(String name, String symbol, String country) throws DuplicateCurrencyException {
        currencyRepository.add(CurrencyFactory.getCurrency(name, symbol, country));
    }

    public void addCurrency(Currency currency) {
        currencyRepository.add(currency);
    }

    public boolean exists(String currencyName) {
        return currencyRepository.exists(getCurrencyByName(currencyName));
    }

    public void showAllCurrenciesInfo() {
        System.out.println("Valutele disponibile: ");
        for (Currency currency : currencyRepository.getRepository()) {
            System.out.println("Nume moneda: " + currency.getCurrencyName() +
                    " - Simbol moneda: " + currency.getCurrencySymbol() +
                    " - Tara: " + currency.getCountry());
        }
    }

    public HashSet<Currency> getAllCurencies() {
        return new HashSet<>(currencyRepository.getRepository());
    }

    public void showCurrencyInfo(String name) throws InvalidCurrencyNameException {
        Currency currency = getCurrencyByName(name);
        if (currency == null) {
            throw new InvalidCurrencyNameException("Nu exista aceasta moneda!");
        }
        System.out.println("Moneda: " + currency.getCurrencyName() + " - Simbol: " + currency.getCurrencySymbol() +
                " - Tara de origine: " + currency.getCountry());
    }

    public void changeCurrencyName(String from, String to) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(from);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(to, currency.getCurrencySymbol(), currency.getCountry());
        currencyRepository.add(newCurrency);
    }

    public void changeCurrencySymbol(String name, String newSymbolName) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(name);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(currency.getCurrencyName(), newSymbolName, currency.getCountry());
        currencyRepository.add(newCurrency);
    }

    public void changeCurrencyCountry(String name, String newCountryName) throws DuplicateCurrencyException {
        Currency currency = getCurrencyByName(name);
        currencyRepository.delete(currency);
        Currency newCurrency = CurrencyFactory.getCurrency(currency.getCurrencyName(), currency.getCurrencySymbol(), newCountryName);
        currencyRepository.add(newCurrency);
    }

    public void removeCurrency(String name) {
        currencyRepository.delete(currencyRepository.getCurrencyByName(name));
    }

    public void readCurrenciesFromFile(String fileName) throws IOException {
        FileReaderService fileReaderService = FileReaderService.getInstance(fileName);
        ArrayList<String> lines = fileReaderService.read();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace("\"", "");
            String[] data = line.split(",");
            String currencyName = data[0].strip();
            String currencySymbol = data[1].strip();
            String currencyCountry = data[2].strip();
            addCurrency(currencyName, currencySymbol, currencyCountry);
        }
    }

    public void writeToFile(String fileName) throws IOException {
        FileWriterService fileWriterService = FileWriterService.getInstance(fileName, false);
        fileWriterService.write(exportFileHeader, false);
        for (Currency currency : getAllCurencies()) {
            String line = "\"" + currency.getCurrencyName() + "\""
                    + ", \"" + currency.getCurrencySymbol() + "\""
                    + ", \"" + currency.getCountry() + "\"";
            fileWriterService.write(line, false);
        }
        fileWriterService.closeFile();
    }
}
