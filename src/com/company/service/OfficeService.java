package com.company.service;

import com.company.domain.Currency;
import com.company.domain.Exchange;
import com.company.domain.ExchangeRate;
import com.company.domain.Office;
import com.company.exceptions.DuplicateExchangeRateException;
import com.company.exceptions.ForbiddenExchangeException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.ExchangeRateRepository;
import com.company.persistence.MoneyRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OfficeService extends MoneyService {

    private static final String exchangeRatesExportFileHeader =
            "\"Moneda de vandut\", \"Moneda de cumparat\", \"Rata de schimb\"";
    private static final String exchangesExportFileHeader =
            "\"Nume Client\", \"Bani vanduti\", \"Bani cumparati\", \"Rata de schimb\"";

    private static ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    private static MoneyRepository officeMoneyRepository = new MoneyRepository();

    private Office office = Office.getInstance();
    private ClientsManager clientsManager = new ClientsManager();
    private CurrencyService currencyService = new CurrencyService();

    @Override
    protected MoneyRepository getMoneyRepository() {
        return officeMoneyRepository;
    }

    public String getOfficeMoney(boolean sorted) {
        return getFormatedMoneyAmounts(sorted);
    }

    /*
        OFFICE METHODS
     */
    public void resetOfficeMoney() {
        MoneyRepository repo = officeMoneyRepository;
        repo.getRepository().clear();
    }

    public void showBiggestMoneyAmount() {
        System.out.println("Moneda in care casa de schimb are cei mai multi bani: " + getBiggestMoneyAmount().getAmount());
    }

    public void showSmallestMoneyAmount() {
        System.out.println("Moneda in care care casa de schimb are cei mai putini bani: " + getSmallestMoneyAmount().getAmount());
    }

    public String getOfficeName() {
        return office.getOfficeName();
    }

    public String getOfficeAddress() {
        return office.getAddress();
    }

    public Office getOffice() {
        return Office.getInstance();
    }

    public Float getMoneyAmount(String currencyName) {
        return super.getMoneyAmount(currencyName);
    }

    public void readOfficeInfoFromFile(String fileName) throws IOException {
        FileReaderService fileReaderService = FileReaderService.getInstance(fileName);
        ArrayList<String> lines = fileReaderService.read();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace("\"", "");
            String[] data = line.split(",");
            String officeName = data[0];
            String officeAddress = data[1];
            office.setOfficeName(officeName);
            office.setAddress(officeAddress);

            String[] allMoney = data[2].split("\\|");
            for (String money : allMoney) {
                String[] temp = money.strip().split(" ");
                String currencyName = temp[1];
                float amount = Float.parseFloat(temp[0]);
                addMoney(currencyName, amount);
            }
        }
    }

    /*
        EXCHANGES METHODS
     */


    public int exchangeMoney(String clientFirstName, String fromCurrencyName, String toCurrencyName, Float amount) {
        if (clientsManager.getMoneyAmount(clientFirstName, fromCurrencyName) < amount) {
            throw new ForbiddenExchangeException("Bani insuficienti");
        }
        Float rate = getExchangeRate(fromCurrencyName, toCurrencyName);
        Float newAmount = rate * amount;
        if (getMoneyAmount(toCurrencyName) < newAmount) {
            throw new ForbiddenExchangeException("Bani insuficienti");
        }
        // Ne-am asigurat ca se poate realiza tranzactia
        clientsManager.decreaseMoney(clientFirstName, fromCurrencyName, amount);
        clientsManager.increaseMoney(clientFirstName, toCurrencyName, newAmount);
        increaseMoney(fromCurrencyName, amount);
        decreaseMoney(toCurrencyName, newAmount);
        // Facem log pentru acest timp de actiune
        Exchange exchange = new ExchangeService().addExchange(clientFirstName, fromCurrencyName,
                toCurrencyName, amount, newAmount, rate);
        return exchange.getExchangeID();
    }

    public Exchange getExchange(Integer exchangeID) {
        return new ExchangeService().getExchange(exchangeID);
    }

    public ArrayList<Exchange> getAllExchanges() {
        return new ArrayList<>(new ExchangeService().getAllExchanges());
    }

    public String getAllExchangesFormatted() {
        StringBuilder text = new StringBuilder();
        for (Exchange exchange : getAllExchanges()) {
            text.append("Clientul ")
                    .append(exchange.getClient().getName())
                    .append(" a schimbat suma de ").append(exchange.getMoneyGiven())
                    .append(" in ")
                    .append(exchange.getMoneyReceived())
                    .append(" la rata de schimb ")
                    .append(exchange.getExchangeRate());
        }
        return text.toString();
    }

    public void readExchangesFromFile(String fileName) throws IOException {
        FileReaderService fileReaderService = FileReaderService.getInstance(fileName);
        ArrayList<String> lines = fileReaderService.read();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace("\"", "");
            String[] data = line.split(",");
            String clientName = data[0].strip();
            String moneyGiven = data[1].strip();
            String moneyReceived = data[2].strip();
            float rate = Float.parseFloat(data[3].strip());

            String[] temp = moneyGiven.split(" ");
            String currencyGivenName = temp[1];
            float amountGiven = Float.parseFloat(temp[0]);

            String[] temp2 = moneyReceived.split(" ");
            String currencyReceivedName = temp2[1];
            float amountReceived = Float.parseFloat(temp2[0]);

            new ExchangeService().addExchange(clientName.split(" ")[0],
                    currencyGivenName, currencyReceivedName, amountGiven, amountReceived, rate);
        }
    }

    public void writeExchangesToFile(String filePath) throws IOException {
        //String path = "./database/" + fileName;
        FileWriterService fileWriterService = FileWriterService.getInstance(filePath, false);
        //append = false -> overwriting
        //Write file header
        fileWriterService.write(exchangesExportFileHeader, false);
        // Formatare text
        for (Exchange exchange : getAllExchanges()) {
            String line = "\"" + exchange.getClient().getName() + "\", "
                    + "\"" + exchange.getMoneyGiven().getAmount() + " " + exchange.getMoneyGiven().getCurrencyName() + "\", "
                    + "\"" + exchange.getMoneyReceived().getAmount() + " " + exchange.getMoneyReceived().getCurrencyName() + "\", "
                    + "\"" + exchange.getExchangeRate() + "\"";
            fileWriterService.write(line, false);
        }
        fileWriterService.closeFile();
    }

    /*
        EXCHANGE RATES METHODS
     */

    public Float getExchangeRate(String fromCurrencyName, String toCurrencyName) {
        Currency from = currencyService.getCurrencyByName(fromCurrencyName);
        Currency to = currencyService.getCurrencyByName(toCurrencyName);
        ExchangeRate exchange = exchangeRateRepository.getExchangeRate(from, to);
        return exchange.getRate();
    }

    public ArrayList<ExchangeRate> getAllExchangeRates() {
        ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
        for (int i = 0; i < exchangeRateRepository.getSize(); i++) {
            exchangeRates.add(exchangeRateRepository.get(i));
        }
        return exchangeRates;
    }

    public String getAllExchangeRatesFormatted() {
        StringBuilder string = new StringBuilder();
        for (ExchangeRate exchangeRate : getAllExchangeRates()) {
            string.append(exchangeRate.getFrom().getCurrencyName())
                    .append(" -> ")
                    .append(exchangeRate.getTo().getCurrencyName())
                    .append(" : ")
                    .append(exchangeRate.getRate())
                    .append(System.getProperty("line.separator"));
        }
        return string.toString();
    }

    public void addExchangeRate(String fromCurrencyName, String toCurrencyName, Float exchangeRate) {
        Currency from = currencyService.getCurrencyByName(fromCurrencyName);
        Currency to = currencyService.getCurrencyByName(toCurrencyName);
        if (from == null || to == null) {
            throw new InvalidCurrencyNameException("Nu exista aceasta valuta");
        }
        if (exchangeRateRepository.exists(new ExchangeRate(from, to, exchangeRate)))
            throw new DuplicateExchangeRateException("Exista deja aceasta conversie");
        exchangeRateRepository.add(new ExchangeRate(from, to, exchangeRate));
        exchangeRateRepository.add(new ExchangeRate(to, from, 1 / exchangeRate));
    }

    public void readExchangeRatesFromFile(String fileName) throws IOException {
        FileReaderService fileReaderService = FileReaderService.getInstance(fileName);
        ArrayList<String> lines = fileReaderService.read();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace("\"", "");
            String[] data = line.split(",");
            String fromCurrency = data[0].strip();
            String toCurrency = data[1].strip();
            float exchangeRate = Float.parseFloat(data[2].strip());
            addExchangeRate(fromCurrency, toCurrency, exchangeRate);
        }
    }

    public void writeExchangeRatesToFile(String filePath) throws IOException {
        FileWriterService fileWriterService = FileWriterService.getInstance(filePath, false);
        fileWriterService.write(exchangeRatesExportFileHeader, false);
        ArrayList<ExchangeRate> exchangeRates = getAllExchangeRates();
        HashMap<Currency, Currency> repetitions = new HashMap<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            //Nu scriem si conversia inversa
            if (repetitions.get(exchangeRate.getTo()) != exchangeRate.getFrom()) {
                String line = "";
                line = "\"" + exchangeRate.getFrom().getCurrencyName() + "\"" +
                        ", \"" + exchangeRate.getTo().getCurrencyName() + "\"" +
                        ", \"" + exchangeRate.getRate() + "\"";
                fileWriterService.write(line, false);
                repetitions.put(exchangeRate.getFrom(), exchangeRate.getTo());
            }
        }
        fileWriterService.closeFile();
    }
}