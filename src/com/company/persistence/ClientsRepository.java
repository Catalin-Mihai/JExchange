/**
 * TODO: Mai multi clienti
 */


package com.company.persistence;

import com.company.domain.Client;
import com.company.exceptions.DuplicateClientException;
import com.company.util.ClientData;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Set;

public class ClientsRepository {

    /**
     * Aceasta clasa contine detaliile clientului -> Client
     * si portofelul/banii clientului -> MoneyRepository
     * Unui client ii este asociat/mapat portofelul.
     */

    private static HashMap<Client, MoneyRepository> clients = new HashMap<>();

    public void add(Client client) throws DuplicateClientException {
        if (exists(client)) throw new DuplicateClientException("Exista deja un client cu acest nume!");
        clients.put(client, new MoneyRepository());
    }

    public void add(Client client, MoneyRepository money) throws DuplicateClientException {
        if (exists(client)) throw new DuplicateClientException("Exista deja un client cu acest nume!");
        clients.put(client, money);
    }

    public ClientData get(Client client) {
        if (!exists(client)) throw new InvalidParameterException("Clientul nu exista");
        return new ClientData(client, clients.get(client));
    }

    public boolean exists(Client client) {
        return clients.containsKey(client);
    }

    public void update(Client oldClient, Client newClient) {
        if (!exists(oldClient)) throw new InvalidParameterException("Clientul nu exista");
        // Inlocuire cheie veche cu cea noua pentru a nu modifica manual toate campurile clientului
        MoneyRepository money = clients.get(oldClient);
        clients.remove(oldClient);
        clients.put(newClient, money);
    }

    public void update(Client client, MoneyRepository moneyRepository) {
        if (!exists(client)) throw new InvalidParameterException("Clientul nu exista");
        clients.remove(client);
        clients.put(client, moneyRepository);
    }

    public Client getClientByFirstName(String firstName) throws InvalidParameterException {
        for (Client key : clients.keySet()) {
            if (key.getFirstName().equals(firstName)) {
                return key;
            }
        }
        throw new InvalidParameterException("Nu exista un client cu acest nume!");
    }

    public Client getClientByName(String name) throws InvalidParameterException {
        for (Client key : clients.keySet()) {
            if (key.getName().equals(name)) {
                return key;
            }
        }
        throw new InvalidParameterException("Nu exista un client cu acest nume!");
    }

    public void remove(Client client) {
        if (!exists(client)) throw new InvalidParameterException("Nu exista acest client!");
        clients.remove(client);
    }

    public Set<Client> getAllClients() {
        return clients.keySet();
    }

    public int getSize() {
        return clients.size();
    }
}
