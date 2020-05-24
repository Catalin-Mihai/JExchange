package com.company.service.entity;

import com.company.domain.CurrencyEntity;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.remote.CurrencyRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;
import com.company.util.factory.CurrencyEntityFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CurrencyService {


    private static final String exportFileHeader = "\"Currency Name\", \"Currency Symbol\", \"Currency Country\"";
    private static final CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

    public CurrencyEntity getCurrencyByName(String name) {
        return currencyRepository.getCurrency(name);
    }

    public void addCurrency(String name, String symbol) {
        currencyRepository.add(CurrencyEntityFactory.getCurrency(name, symbol));
    }

    public void addCurrency(String name, String symbol, String country) {
        currencyRepository.add(CurrencyEntityFactory.getCurrency(name, symbol, country));
    }

    public void addCurrency(CurrencyEntity currency) {
        currencyRepository.add(currency);
    }

    public void showAllCurrenciesInfo() {
        System.out.println("Valutele disponibile: ");
        for (CurrencyEntity currency : currencyRepository.getAll()) {
            System.out.println("Nume moneda: " + currency.getName() +
                    " - Simbol moneda: " + currency.getSymbol() +
                    " - Tara: " + currency.getCountry());
        }
    }

    public HashSet<CurrencyEntity> getAllCurencies() {
        return new HashSet<>(currencyRepository.getAll());
    }

    public void showCurrencyInfo(String name) {
        CurrencyEntity currency = getCurrencyByName(name);
        if (currency == null) {
            throw new InvalidCurrencyNameException("Nu exista aceasta moneda!");
        }
        System.out.println("Moneda: " + currency.getName() + " - Simbol: " + currency.getSymbol() +
                " - Tara de origine: " + currency.getCountry());
    }

    public void changeCurrencyName(String from, String to) {
        CurrencyEntity currency = getCurrencyByName(from);
        currency.setName(to);
        currencyRepository.update(currency);
    }

    public void changeCurrencySymbol(String name, String newSymbolName) {

        CurrencyEntity currency = getCurrencyByName(name);
        currency.setSymbol(newSymbolName);
        currencyRepository.update(currency);
    }

    public void changeCurrencyCountry(String name, String newCountryName) {
        CurrencyEntity currency = getCurrencyByName(name);
        currency.setCountry(newCountryName);
        currencyRepository.update(currency);
    }

    public void removeCurrency(String name) {
        CurrencyEntity currencyEntity = currencyRepository.getCurrency(name);

        if (currencyEntity == null)
            throw new InvalidCurrencyNameException("Nu exista aceasta valuta");

        currencyRepository.delete(currencyEntity);
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
        for (CurrencyEntity currency : getAllCurencies()) {
            String line = "\"" + currency.getName() + "\""
                    + ", \"" + currency.getSymbol() + "\""
                    + ", \"" + currency.getCountry() + "\"";
            fileWriterService.write(line, false);
        }
        fileWriterService.closeFile();
    }
}
