package com.company.util.factory;

import com.company.domain.ClientEntity;
import com.company.exceptions.DuplicateOfficeException;
import com.company.service.entity.ClientsManager;

public class ClientEntityFactory {

    public static ClientEntity getClient(String firstName, String lastName) {
        if (new ClientsManager().getClientByFirstName(firstName) != null)
            throw new DuplicateOfficeException("Exista deja acest client");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName(firstName);
        clientEntity.setLastName(lastName);
        return clientEntity;
    }
}
