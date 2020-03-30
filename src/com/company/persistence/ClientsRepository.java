/**
 * TODO: Mai multi clienti
 */


package com.company.persistence;

import com.company.domain.Client;
import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ClientsRepository implements GenericPersonsRepository<Client> {

    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public void add(Client entity) throws DuplicateClientException {
        if(exists(entity)) throw new DuplicateClientException("Exista deja un client cu acest nume!");
        clients.add(entity);
    }

    @Override
    public Client get(int index) {
        return clients.get(index);
    }

    public int getIndex(Client client) {
        for(int i = 0; i < clients.size(); i++){
            if(client.equals(clients.get(i))){
                return i;
            }
        }
        return -1;
    }

    public boolean exists(Client client){
        return clients.contains(client);
    }

    @Override
    public void update(Client entity) {
        int index = getIndex(entity);
        if(index == -1) throw new InvalidParameterException("Nu exista acest client!");
        clients.set(index, entity);
    }

    public int getClientIndexByFirstName(String firstName){
        for(int i = 0; i < clients.size(); i++){
            if(clients.get(i).getFirstName().equals(firstName)){
                return i;
            }
        }
        return -1;
    }

    public Client getClientByFirstName(String firstName) throws InvalidParameterException{
        for(int i = 0; i < clients.size(); i++){
            if(clients.get(i).getFirstName().equals(firstName)){
                return clients.get(i);
            }
        }
        throw new InvalidParameterException("Nu exista un client cu acest nume!");
    }

    @Override
    public void remove(Client entity) {
        int index = getIndex(entity);
        if(index == -1) throw new InvalidParameterException("Nu exista acest client!");
        clients.remove(index);
    }

    public ArrayList<Client> getRepository(){
        return clients;
    }

    @Override
    public int getSize() {
        return clients.size();
    }
}
