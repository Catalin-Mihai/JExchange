package com.company.service.entity;

import com.company.domain.ClientEntity;
import com.company.domain.ClientMoneyEntity;
import com.company.exceptions.InvalidClientException;
import com.company.persistence.remote.ClientRepository;
import com.company.service.io.FileReaderService;
import com.company.service.io.FileWriterService;
import com.company.util.factory.ClientEntityFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientsManager {

    private static final ClientRepository clientRepository = ClientRepository.getInstance();
    private static final String exportFileHeader = "\"First name\", \"Last name\", \"Money\"";


    public ClientEntity getClient(String firstName) {
        ClientEntity clientEntity = getClientByFirstName(firstName);
        if (clientEntity == null)
            throw new InvalidClientException("Nu exista acest client");
        return clientEntity;
    }

    public ClientEntity getClientByFirstName(String firstName) {
        return clientRepository.getClient(firstName);
    }

    public void addClient(String firstName, String lastName) {

        ClientEntity client = ClientEntityFactory.getClient(firstName, lastName);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        clientRepository.add(client);
    }

    public void removeClient(String firstName) {
        clientRepository.delete(getClient(firstName));
    }

    public void changeClientFirstName(String fromFirstName, String toFirstName) {
        ClientEntity client = getClient(fromFirstName);
        client.setFirstName(toFirstName);
        clientRepository.update(client);
    }

    public Double getMoneyAmount(String firstName, String currencyName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getMoneyAmount(currencyName);
    }

    public void changeClientLastName(String fromFirstName, String toLastName) {
        ClientEntity client = getClient(fromFirstName);
        client.setLastName(toLastName);
        clientRepository.update(client);
    }

    public void addMoney(String firstName, String currencyName, Double amount) {
        ClientEntity client = getClient(firstName);
        new ClientService(client)
                .addMoney(currencyName, amount);
    }

    public void decreaseMoney(String firstName, String currencyName, Double amount) {
        ClientEntity client = getClient(firstName);
        new ClientService(client)
                .decreaseMoney(currencyName, amount);
    }

    public void increaseMoney(String firstName, String currencyName, Double amount) {
        ClientEntity client = getClient(firstName);
        new ClientService(client)
                .increaseMoney(currencyName, amount);
    }

    public Set<ClientEntity> getAllClients() {
        return new HashSet<>(clientRepository.getAll());
    }

    public String getClientInfoFormated(String firstName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getClientInfoFormatted();
    }

    public String getAllClientsInfoFormated() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Informatii despre toti clientii\n");
        for (ClientEntity client : clientRepository.getAll()) {
            stringBuilder.append(new ClientService(client).getClientInfoFormatted());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public List<ClientMoneyEntity> getClientAllMoney(String firstName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getAllMoney();
    }

    public ClientMoneyEntity getClientMoney(String firstName, String currencyName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getMoney(currencyName);
    }

    public void resetClientMoney(String firstName, String currencyName) {
        ClientEntity client = getClient(firstName);
        new ClientService(client).resetMoney(currencyName);
    }

    public void resetClientAllMoney(String firstName) {
        ClientEntity client = getClient(firstName);
        new ClientService(client).resetAllMoney();
    }

    public void showClientMoney(String firstName, boolean sorted) {
        ClientEntity client = getClient(firstName);
        new ClientService(client).showClientMoney(true);
    }

    public String getClientBiggestMoneyAmount(String firstName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getBiggestMoneyAmountFormatted();
    }

    public String getClientSmallestMoneyAmount(String firstName) {
        ClientEntity client = getClient(firstName);
        return new ClientService(client).getSmallestMoneyAmountFormatted();
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
                    Double amount = Double.parseDouble(temp[0]);
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
        for (ClientEntity client : getAllClients()) {
            StringBuilder line = new StringBuilder("\"" + client.getFirstName() + "\", "
                    + "\"" + client.getLastName() + "\"");
            if (getClientAllMoney(client.getFirstName()).size() == 0) {
                line.append(", \"\"");
            } else {
                line.append(", \"");
                for (ClientMoneyEntity money : getClientAllMoney(client.getFirstName())) {
                    line.append(money.getAmount()).append(" ").append(money.getCurrency().getName());
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
