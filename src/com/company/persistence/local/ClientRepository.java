package com.company.persistence.local;

import com.company.domain.ClientEntity;
import com.company.exceptions.DuplicateClientException;
import com.company.persistence.GenericRepositoryInterface;

import java.security.InvalidParameterException;
import java.util.*;

public class ClientRepository implements GenericRepositoryInterface<ClientEntity> {

    /**
     * Aceasta clasa contine detaliile clientului -> Client
     * si portofelul/banii clientului -> MoneyRepository
     * Unui client ii este asociat/mapat portofelul.
     */

    private static ClientRepository instance = null;

    public static ClientRepository getInstance() {
        if(instance == null)
            instance = new ClientRepository();
        return instance;
    }

    private ClientRepository() {}

    //Fiecarui clientId ii corespunde un ClientEntity
    private static final HashMap<Integer, ClientEntity> clients = new HashMap<>();

    @Override
    public void add(ClientEntity client) throws DuplicateClientException {
        if (exists(client)) throw new DuplicateClientException("Exista deja un client cu acest nume!");
        clients.put(client.getId(), client);
    }

    @Override
    public ClientEntity get(int id) {
        return clients.get(id);
    }

    @Override
    public void update(ClientEntity entity) {
        clients.put(entity.getId(), entity);
    }

    public ClientEntity get(ClientEntity client) {
        if (!exists(client)) throw new InvalidParameterException("Clientul nu exista");
        return get(client.getId());
    }

    public boolean exists(ClientEntity client) {
        return clients.containsKey(client.getId());
    }

    public ClientEntity getClient(String firstName) throws InvalidParameterException {
        for (ClientEntity client : clients.values()) {
            if (client.getFirstName().equals(firstName)) {
                return client;
            }
        }
        throw new InvalidParameterException("Nu exista un client cu acest nume!");
    }

    public void delete(ClientEntity client) {
        if (!exists(client)) throw new InvalidParameterException("Nu exista acest client!");
        clients.remove(client.getId());
    }

    @Override
    public int getSize() {
        return clients.size();
    }

    public Collection<ClientEntity> getAll()
    {
        return clients.values();
    }
}
