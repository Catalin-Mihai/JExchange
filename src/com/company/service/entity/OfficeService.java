package com.company.service.entity;

import com.company.domain.*;
import com.company.exceptions.*;
import com.company.persistence.remote.ExchangeRateRepository;
import com.company.persistence.remote.OfficeMoneyRepository;
import com.company.persistence.remote.OfficeRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;
import com.company.util.factory.OfficeEntityFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class OfficeService {

    private static final String exchangeRatesExportFileHeader =
            "\"Moneda de vandut\", \"Moneda de cumparat\", \"Rata de schimb\"";
    private static final String exchangesExportFileHeader =
            "\"Nume Client\", \"Bani vanduti\", \"Bani cumparati\", \"Rata de schimb\"";

    private static final ExchangeRateRepository exchangeRateRepository = ExchangeRateRepository.getInstance();
    private static final OfficeMoneyRepository officeMoneyRepository = OfficeMoneyRepository.getInstance();
    private static final OfficeRepository officeRepository = OfficeRepository.getInstance();

    private final ClientsManager clientsManager = new ClientsManager();
    private final CurrencyService currencyService = new CurrencyService();

    /*
        OFFICE METHODS
     */

    public OfficeEntity getOffice(String officeName) {
        return officeRepository.getOffice(officeName);
    }

    public OfficeMoneyEntity getOfficeMoney(OfficeEntity officeEntity, CurrencyEntity currencyEntity) {
        OfficeMoneyEntity moneyEntity = officeMoneyRepository.getMoney(officeEntity, currencyEntity);
        if (moneyEntity == null)
            throw new InvalidMoneyException("Nu exista acesti bani in office");
        return moneyEntity;
    }

    public void addOffice(String officeName, String address) {
        OfficeEntity newOffice = OfficeEntityFactory.getOffice(officeName, address);
        officeRepository.add(newOffice);
    }

    public String getOfficeAllMoneyFormated(String officeName, boolean sorted) {
        return getFormatedMoneyAmounts(officeName, sorted);
    }

    public List<OfficeMoneyEntity> getOfficeAllMoney(String officeName) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        return officeMoneyRepository.getAllMoney(officeEntity);
    }

    public void resetOfficeAllMoney(String officeName) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        officeMoneyRepository.resetAllMoney(officeEntity);
    }

    public void resetOfficeMoney(String officeName, String currencyName) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        CurrencyEntity currencyEntity = currencyService.getCurrencyByName(currencyName);
        OfficeMoneyEntity money = getOfficeMoney(officeEntity, currencyEntity);
        money.setAmount(0d);
        officeMoneyRepository.update(money);
    }

    public OfficeMoneyEntity getBiggestMoneyAmount(OfficeEntity officeEntity) {
        if (officeMoneyRepository.getAllMoneySorted(officeEntity, false) == null)
            return null;
        return officeMoneyRepository.getAllMoneySorted(officeEntity, false).get(0);
    }

    public OfficeMoneyEntity getSmallestMoneyAmount(OfficeEntity officeEntity) {
        if (officeMoneyRepository.getAllMoneySorted(officeEntity, false) == null)
            return null;
        return officeMoneyRepository.getAllMoneySorted(officeEntity, true).get(0);
    }

    public void showBiggestMoneyAmount(String officeName) {
        OfficeEntity officeEntity = officeRepository.getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        OfficeMoneyEntity officeMoneyEntity = getBiggestMoneyAmount(officeEntity);
        if (officeMoneyEntity == null)
            System.out.println("Nu exista bani!");
        else
            System.out.println("Moneda in care casa de schimb are cei mai multi bani: " +
                    officeMoneyEntity.getCurrency().getName() + " -> " + officeMoneyEntity.getAmount()
            );
    }

    public void showSmallestMoneyAmount(String officeName) {
        OfficeEntity officeEntity = officeRepository.getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        OfficeMoneyEntity officeMoneyEntity = getSmallestMoneyAmount(officeEntity);
        if (officeMoneyEntity == null)
            System.out.println("Nu exista bani!");
        else
            System.out.println("Moneda in care casa de schimb are cei mai multi bani: " +
                    officeMoneyEntity.getCurrency().getName() + " -> " + officeMoneyEntity.getAmount()
            );
    }

    public Double getMoneyAmount(String officeName, String currencyName) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        return getOfficeMoney(officeEntity,
                currencyService.getCurrencyByName(currencyName)).getAmount();
    }

    public String getFormatedMoneyAmounts(String officeName, boolean sorted) {
        OfficeEntity officeEntity = officeRepository.getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        ArrayList<OfficeMoneyEntity> allMoney = (ArrayList<OfficeMoneyEntity>) officeMoneyRepository.getAllMoney(officeEntity);
        if (allMoney == null)
            return "Nu exista bani!";
        if (sorted) {
            allMoney.sort(new Comparator<OfficeMoneyEntity>() {
                @Override
                public int compare(OfficeMoneyEntity o1, OfficeMoneyEntity o2) {
                    if (o1.getAmount() > o2.getAmount())
                        return 1;
                    else if (o1.getAmount() < o2.getAmount())
                        return -1;
                    return 0;
                }
            });
        }
        StringBuilder string = new StringBuilder();
        for (OfficeMoneyEntity money : allMoney) {
            string.append(money.getCurrency().getName()).append(" : ").append(money.getAmount());
        }
        return string.toString();
    }

    public void addMoney(String officeName, String currencyName, Double amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        OfficeMoneyEntity officeMoneyEntity = new OfficeMoneyEntity();
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        officeMoneyEntity.setOffice(officeEntity);
        officeMoneyEntity.setAmount(amount);
        officeMoneyEntity.setCurrency(currencyService.getCurrencyByName(currencyName));
        officeMoneyRepository.add(officeMoneyEntity);
    }

    public void increaseMoney(String officeName, String currencyName, Double amount) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        OfficeMoneyEntity officeMoneyEntity = getOfficeMoney(officeEntity,
                currencyService.getCurrencyByName(currencyName));
        officeMoneyEntity.setAmount(officeMoneyEntity.getAmount() + amount);
        officeMoneyRepository.update(officeMoneyEntity);
    }

    public void decreaseMoney(String officeName, String currencyName, Double amount) {
        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");
        OfficeMoneyEntity officeMoneyEntity = getOfficeMoney(officeEntity,
                currencyService.getCurrencyByName(currencyName));
        officeMoneyEntity.setAmount(officeMoneyEntity.getAmount() - amount);
        officeMoneyRepository.update(officeMoneyEntity);
    }

/*    public void readOfficeInfoFromFile(String fileName) throws IOException {
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
    }*/

    /*
        EXCHANGES METHODS
     */


    public int exchangeMoney(String officeName, String clientFirstName,
                             String fromCurrencyName, String toCurrencyName, Double amount) {
        if (clientsManager.getMoneyAmount(clientFirstName, fromCurrencyName) < amount) {
            throw new ForbiddenExchangeException("Bani insuficienti");
        }
        ExchangeRateEntity exchangeRate = getExchangeRate(fromCurrencyName, toCurrencyName);
        if (exchangeRate == null)
            throw new InvalidExchangeRateException("Nu exista conversie intre aceste monede");
        Double rate = exchangeRate.getRate();

        double newAmount = rate * amount;
        if (getMoneyAmount(officeName, toCurrencyName) < newAmount) {
            throw new ForbiddenExchangeException("Bani insuficienti");
        }

        //Client
        // Ne-am asigurat ca se poate realiza tranzactia
        clientsManager.decreaseMoney(clientFirstName, fromCurrencyName, amount);

        if (clientsManager.getClientMoney(clientFirstName, toCurrencyName) == null) {
            //Se adauga o noua valuta in cazul in care nu exista
            clientsManager.addMoney(clientFirstName, toCurrencyName, 0d);
        }
        clientsManager.increaseMoney(clientFirstName, toCurrencyName, newAmount);

        //Office
        decreaseMoney(officeName, toCurrencyName, newAmount);

        OfficeEntity officeEntity = getOffice(officeName);
        if (officeEntity == null)
            throw new InvalidOfficeException("Nu exista acest office");

        if (officeMoneyRepository.getMoney(
                officeEntity, currencyService.getCurrencyByName(fromCurrencyName)) == null) {
            //Se adauga o noua valuta in cazul in care nu exista
            addMoney(officeName, fromCurrencyName, 0d);
        }
        increaseMoney(officeName, fromCurrencyName, amount);


        // Facem log pentru acest timp de actiune
        ExchangeEntity exchange = new ExchangeService().addExchange(officeName, clientFirstName, fromCurrencyName,
                toCurrencyName, amount, newAmount, exchangeRate);
        return exchange.getId();
    }

    public ExchangeEntity getExchange(Integer exchangeID) {
        return new ExchangeService().getExchange(exchangeID);
    }

    public ArrayList<ExchangeEntity> getAllExchanges() {
        return new ArrayList<>(new ExchangeService().getAllExchanges());
    }

    public String getAllExchangesFormatted() {
        StringBuilder text = new StringBuilder();
        for (ExchangeEntity exchange : getAllExchanges()) {
            text.append("Clientul ")
                    .append(exchange.getClient().getName())
                    .append(" a schimbat suma de ").append(exchange.getMoneyGiven())
                    .append(" in ")
                    .append(exchange.getMoneyReceived())
                    .append(" la rata de schimb ")
                    .append(exchange.getExchangeRate().getRate());
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
            String officeName = data[0].strip();
            String clientName = data[1].strip();
            String moneyGiven = data[2].strip();
            String moneyReceived = data[3].strip();
            float rate = Float.parseFloat(data[4].strip());

            String[] temp = moneyGiven.split(" ");
            String currencyGivenName = temp[1];
            Double amountGiven = Double.parseDouble(temp[0]);

            String[] temp2 = moneyReceived.split(" ");
            String currencyReceivedName = temp2[1];
            Double amountReceived = Double.parseDouble(temp2[0]);

            ExchangeRateEntity exchangeRateEntity = getExchangeRate(currencyGivenName, currencyReceivedName);

            new ExchangeService().addExchange(officeName, clientName.split(" ")[0], currencyGivenName,
                    currencyReceivedName, amountGiven, amountReceived, exchangeRateEntity);
        }
    }

    public void writeExchangesToFile(String filePath) throws IOException {
        //String path = "./database/" + fileName;
        FileWriterService fileWriterService = FileWriterService.getInstance(filePath, false);
        //append = false -> overwriting
        //Write file header
        fileWriterService.write(exchangesExportFileHeader, false);
        // Formatare text
        for (ExchangeEntity exchange : getAllExchanges()) {
            String line = "\"" + exchange.getClient().getName() + "\", "
                    + "\"" + exchange.getMoneyGiven().getAmount() + " " + exchange.getMoneyGiven().getCurrency().getName() + "\", "
                    + "\"" + exchange.getMoneyReceived().getAmount() + " " + exchange.getMoneyReceived().getCurrency().getName() + "\", "
                    + "\"" + exchange.getExchangeRate() + "\"";
            fileWriterService.write(line, false);
        }
        fileWriterService.closeFile();
    }

    /*
        EXCHANGE RATES METHODS
     */

    public ExchangeRateEntity getExchangeRate(String fromCurrencyName, String toCurrencyName) {
        CurrencyEntity from = currencyService.getCurrencyByName(fromCurrencyName);
        CurrencyEntity to = currencyService.getCurrencyByName(toCurrencyName);
        return exchangeRateRepository.getExchangeRate(from, to);
    }

    public ArrayList<ExchangeRateEntity> getAllExchangeRates() {
        return (ArrayList<ExchangeRateEntity>) exchangeRateRepository.getAll();
    }

    public String getAllExchangeRatesFormatted() {
        StringBuilder string = new StringBuilder();
        for (ExchangeRateEntity exchangeRate : getAllExchangeRates()) {
            string.append(exchangeRate.getFromCurrency().getName())
                    .append(" -> ")
                    .append(exchangeRate.getToCurrency().getName())
                    .append(" : ")
                    .append(exchangeRate.getRate())
                    .append(System.lineSeparator());
        }
        return string.toString();
    }

    public void addExchangeRate(String fromCurrencyName, String toCurrencyName, Double exchangeRate) {
        CurrencyEntity from = currencyService.getCurrencyByName(fromCurrencyName);
        CurrencyEntity to = currencyService.getCurrencyByName(toCurrencyName);
        if (from == null || to == null) {
            throw new InvalidCurrencyNameException("Nu exista aceasta valuta");
        }
        if (exchangeRateRepository.getExchangeRate(from, to) != null)
            throw new DuplicateExchangeRateException("Exista deja aceasta conversie");
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        exchangeRateEntity.setFromCurrency(from);
        exchangeRateEntity.setToCurrency(to);
        exchangeRateEntity.setRate(exchangeRate);
        exchangeRateRepository.add(exchangeRateEntity); //directa
        exchangeRateEntity.setRate(1 / exchangeRate);
        exchangeRateEntity.setToCurrency(from);
        exchangeRateEntity.setFromCurrency(to);
        exchangeRateRepository.add(exchangeRateEntity); //inversa
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
            addExchangeRate(fromCurrency, toCurrency, (double) exchangeRate);
        }
    }

    public void writeExchangeRatesToFile(String filePath) throws IOException {
        FileWriterService fileWriterService = FileWriterService.getInstance(filePath, false);
        fileWriterService.write(exchangeRatesExportFileHeader, false);
        ArrayList<ExchangeRateEntity> exchangeRates = getAllExchangeRates();
        HashMap<CurrencyEntity, CurrencyEntity> repetitions = new HashMap<>();
        for (ExchangeRateEntity exchangeRate : exchangeRates) {
            //Nu scriem si conversia inversa
            if (repetitions.get(exchangeRate.getToCurrency()) != exchangeRate.getFromCurrency()) {
                String line = "";
                line = "\"" + exchangeRate.getFromCurrency().getName() + "\"" +
                        ", \"" + exchangeRate.getToCurrency().getName() + "\"" +
                        ", \"" + exchangeRate.getRate() + "\"";
                fileWriterService.write(line, false);
                repetitions.put(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());
            }
        }
        fileWriterService.closeFile();
    }
}