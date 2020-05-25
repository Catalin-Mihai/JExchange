package com.company.persistence.remote;

import com.company.domain.ExchangeEntity;

public class ExchangeRepository extends GenericRepository<ExchangeEntity> {

    private static final Class<ExchangeEntity> ENTITY_CLASS = ExchangeEntity.class;
    private static ExchangeRepository instance = null;

    private ExchangeRepository() {
    }

    public static ExchangeRepository getInstance() {
        if (instance == null) {
            instance = new ExchangeRepository();
        }
        return instance;
    }

    @Override
    Class<ExchangeEntity> getEntityClassName() {
        return ENTITY_CLASS;
    }

}
