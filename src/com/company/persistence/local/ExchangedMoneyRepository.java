package com.company.persistence.local;

import com.company.domain.ClientMoneyEntity;
import com.company.domain.ExchangedMoneyEntity;

public class ExchangedMoneyRepository extends GenericArrayListRepository<ExchangedMoneyEntity>{

    public static ExchangedMoneyRepository instance;

    public static ExchangedMoneyRepository getInstance() {
        if(instance == null){
            instance = new ExchangedMoneyRepository();
        }
        return instance;
    }

    private ExchangedMoneyRepository() {}

    @Override
    public ExchangedMoneyEntity get(int id) {
        for(ExchangedMoneyEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }
}
