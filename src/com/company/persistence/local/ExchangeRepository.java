package com.company.persistence.local;

import com.company.domain.ExchangeEntity;

public class ExchangeRepository extends GenericArrayListRepository<ExchangeEntity> {

    private static ExchangeRepository instance;

    public static ExchangeRepository getInstance() {
        if(instance == null){
            instance = new ExchangeRepository();
        }
        return instance;
    }

    private ExchangeRepository() {}

    @Override
    public ExchangeEntity get(int id) {
        for(ExchangeEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }
}
