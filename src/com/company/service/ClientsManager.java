package com.company.service;

import com.company.domain.Client;
import com.company.domain.Money;
import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.ClientsRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ClientsManager {

    private static ClientsRepository clientsRepository = new ClientsRepository();
    private static String exportFileHeader = "\"First name\", \"Last name\", \"Money\"";

    public void addClient(String firstName, String lastName) throws DuplicateClientException {
        clientsRepository.add(new Client(firstName, lastName));
    }

    public void removeClient(String firstName) {
        clientsRepository.remove(clientsRepository.getClientByFirstName(firstName));
    }

    public Client getClientByFirstName(String firstName) {
        return clientsRepository.getClientByFirstName(firstName);
    }

    public Client getClientByName(String name) {
        return clientsRepository.getClientByName(name);
    }

    public void changeClientFirstName(String fromFirstName, String toFirstName) {
        Client oldClient = clientsRepository.getClientByFirstName(fromFirstName);
        Client newClient = new Client(toFirstName, oldClient.getLastName());
        clientsRepository.update(oldClient, newClient);
    }

    public Float getMoneyAmount(String firstName, String currencyName) {
        Client client = clientsRepository.getClientByFirstName(firstName);
        return new ClientService(client).getMoneyAmount(currencyName);
    }

    public void changeClientLastName(String fromFirstName, String toLastName) {
        Client oldClient = clientsRepository.getClientByFirstName(fromFirstName);
        Client newClient = new Client(oldClient.getFirstName(), toLastName);
        clientsRepository.update(oldClient, newClient);
    }

    public void addMoney(String firstName, String currencyName, Float amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        Client client = clientsRepository.getClientByFirstName(firstName);
        new ClientService(client)
                .addMoney(currencyName, amount);
    }

    public void decreaseMoney(String firstName, String currencyName, Float amount) throws InvalidCurrencyNameException {
        Client client = clientsRepository.getClientByFirstName(firstName);
        new ClientService(client)
                .decreaseMoney(currencyName, amount);
    }

    public void increaseMoney(String firstName, String currencyName, Float amount) throws InvalidCurrencyNameException {
        Client client = clientsRepository.getClientByFirstName(firstName);
        new ClientService(client)
                .increaseMoney(currencyName, amount);
    }

    public Set<Client> getAllClients() {
        return clientsRepository.getAllClients();
    }

    public String getClientInfoFormated(String firstName) {
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getClientInfoFormatted();
    }

    public String getAllClientsInfoFormated() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Informatii despre toti clientii\n");
        for (Client client : clientsRepository.getAllClients()) {
            stringBuilder.append(new ClientService(client).getClientInfoFormatted());
        }
        return stringBuilder.toString();
    }

    public ArrayList<Money> getClientAllMoney(String firstName) {
        Client client = clientsRepository.getClientByFirstName(firstName);
        return new ClientService(client).getMoneyRepository().getRepository();
    }

    public Money getClientMoney(String firstName, String currencyName) {
        Client client = clientsRepository.getClientByFirstName(firstName);
        return new ClientService(client).getMoney(currencyName);
    }

    public void resetClientMoney(String firstName, String currencyName) throws InvalidCurrencyNameException {
        Client client = clientsRepository.getClientByFirstName(firstName);
        new ClientService(client).resetMoney(currencyName);
    }

    public void resetClientAllMoney(String firstName) {
        Client client = clientsRepository.getClientByFirstName(firstName);
        new ClientService(client).resetAllMoney();
    }

    public void showClientMoney(String firstName, boolean sorted) {
        new ClientService(clientsRepository.getClientByFirstName(firstName)).showClientMoney(true);
    }

    public String getClientBiggestMoneyAmount(String firstName) {
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getBiggestMoneyAmountFormatted();
    }

    public String getClientSmallestMoneyAmount(String firstName) {
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getSmallestMoneyAmountFormatted();
    }

    public void readClientsFromFile(String fileName) throws IOException {
        FileReaderService fileReaderService = FileReaderService.getInstance(fileName);
        ArrayList<String> lines = fileReaderService.read();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.replace("\"", "");
            String[] data = line.split(",");
            String firstName = data[0].strip();
            String lastName = data[1].strip();
            addClient(firstName, lastName);

            String[] allMoney = data[2].split("\\|");
            for (String money : allMoney) {
                if (money.strip().length() != 0) {
                    String[] temp = money.strip().split(" ");
                    String currencyName = temp[1];
                    float amount = Float.parseFloat(temp[0]);
                    addMoney(firstName, currencyName, amount);
                }
            }
        }
    }

    public void writeClientsToFile(String filePath) throws IOException {
        //String path = "./database/" + fileName;
        FileWriterService fileWriterService = FileWriterService.getInstance(filePath, false);
        //append = false -> overwriting
        //Write file header
        fileWriterService.write(exportFileHeader, false);
        // Formatare text
        for (Client client : getAllClients()) {
            System.out.println(client.getName());
            StringBuilder line = new StringBuilder("\"" + client.getFirstName() + "\", "
                    + "\"" + client.getLastName() + "\"");
            if (getClientAllMoney(client.getFirstName()).size() == 0) {
                line.append(", \"\"");
            } else {
                line.append(", \"");
                for (Money money : getClientAllMoney(client.getFirstName())) {
                    line.append(money.getAmount()).append(" ").append(money.getCurrencyName());
                    line.append(" | ");
                }
                //Delete the last separator
                line.delete(line.length() - 3, line.length());
                line.append("\"");
            }
            fileWriterService.write(line.toString(), false);
        }
        fileWriterService.closeFile();
    }
}
