package com.company.persistence.local;

import com.company.domain.ClientEntity;
import com.company.domain.ClientMoneyEntity;
import com.company.domain.CurrencyEntity;
import com.company.persistence.MoneyRepositoryInterface;

import java.util.ArrayList;
import java.util.Comparator;

public class ClientMoneyRepository
        extends GenericArrayListRepository<ClientMoneyEntity>
        implements MoneyRepositoryInterface<ClientMoneyEntity, ClientEntity> {

    public static ClientMoneyRepository instance;

    public static ClientMoneyRepository getInstance() {
        if(instance == null){
            instance = new ClientMoneyRepository();
        }
        return instance;
    }

    private ClientMoneyRepository() {}

    @Override
    public ClientMoneyEntity get(int id) {
        for (ClientMoneyEntity entity : repo) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void resetAllMoney(ClientEntity client){
        for (ClientMoneyEntity entity : repo) {
            if(entity.getId() == client.getId())
                entity.setAmount(0d);
        }
    }

    @Override
    public ArrayList<ClientMoneyEntity> getAllMoneySorted(ClientEntity ofEntity, boolean asc){
        ArrayList<ClientMoneyEntity> money = getAllMoney(ofEntity);
        money.sort(new Comparator<ClientMoneyEntity>() {
            @Override
            public int compare(ClientMoneyEntity o1, ClientMoneyEntity o2) {
                if(asc)
                    return o1.getAmount().compareTo(o2.getAmount());
                else
                    return (-1)*o1.getAmount().compareTo(o2.getAmount());
            }
        });
        return money;
    }

    @Override
    public ArrayList<ClientMoneyEntity> getAllMoney(ClientEntity ofEntity) {
        ArrayList<ClientMoneyEntity> lista = new ArrayList<>();
        for (ClientMoneyEntity entity : repo) {
            if(entity.getId() == ofEntity.getId())
                lista.add(entity);
        }
        return lista;
    }

    @Override
    public ClientMoneyEntity getMoney(ClientEntity client, CurrencyEntity currencyEntity){
        for(ClientMoneyEntity entity: repo){
            if(entity.getCurrency().getId() == currencyEntity.getId()
                    && client.getId() == entity.getId()){
                return entity;
            }
        }
        return null;
    }
}
