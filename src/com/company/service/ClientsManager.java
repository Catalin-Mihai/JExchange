package com.company.service;

import com.company.domain.Client;
import com.company.domain.Money;
import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.persistence.ClientsRepository;
import com.company.persistence.MoneyRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ClientsManager{

    private static ClientsRepository clientsRepository = new ClientsRepository();

    public void addClient(String firstName, String lastName) throws DuplicateClientException {
        clientsRepository.add(new Client(firstName, lastName));
    }

    public void removeClient(String firstName){
        clientsRepository.remove(clientsRepository.getClientByFirstName(firstName));
    }

    public void changeClientFirstName(String from_firstName, String to_firstName){
        int index = clientsRepository.getClientIndexByFirstName(from_firstName);
        if(index == -1) throw new InvalidParameterException("Nu exista un client cu acest nume!");
        clientsRepository.get(index).setFirstName(to_firstName);
    }

    public void changeClientLastName(String from_firstName, String to_lastName){
        int index = clientsRepository.getClientIndexByFirstName(from_firstName);
        if(index == -1) throw new InvalidParameterException("Nu exista un client cu acest nume!");
        clientsRepository.get(index).setFirstName(to_lastName);
    }

    public void addMoney(String firstName, String currencyName, int amount) throws DuplicateCurrencyException, InvalidCurrencyNameException {
        new ClientService(clientsRepository.getClientByFirstName(firstName))
                .addMoney(currencyName, amount);
    }

    public void decreaseMoney(String firstName, String currencyName, int amount) throws InvalidCurrencyNameException {
        new ClientService(clientsRepository.getClientByFirstName(firstName))
                .decreaseMoney(currencyName, amount);
    }

    public void increaseMoney(String firstName, String currencyName, int amount) throws InvalidCurrencyNameException {
        new ClientService(clientsRepository.getClientByFirstName(firstName))
                .increaseMoney(currencyName, amount);
    }

    public ArrayList<Client> getAllClients(){
        return clientsRepository.getRepository();
    }

    public String getClientInfoFormated(String firstName){
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getClientInfoFormatted();
    }

    public String getAllClientsInfoFormated(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Informatii despre toti clientii\n");
        for (Client client: clientsRepository.getRepository()){
            stringBuilder.append(new ClientService(client).getClientInfoFormatted());
        }
        return stringBuilder.toString();
    }

    public ArrayList<Money> getClientMoney(String firstName){
        return clientsRepository.getClientByFirstName(firstName).getMoneyRepository().getRepository();
    }

    public void resetClientMoney(String firstName){
        MoneyRepository repo = clientsRepository.getClientByFirstName(firstName).getMoneyRepository();
        repo.getRepository().clear();
    }

    public void showClientMoney(String firstName, boolean sorted){
        new ClientService(clientsRepository.getClientByFirstName(firstName)).showClientMoney(true);
    }

    public String getClientBiggestMoneyAmount(String firstName){
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getBiggestMoneyAmountFormatted();
    }

    public String getClientSmallestMoneyAmount(String firstName){
        return new ClientService(clientsRepository.getClientByFirstName(firstName)).getSmallestMoneyAmountFormatted();
    }

}
