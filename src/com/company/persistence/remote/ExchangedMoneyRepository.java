package com.company.persistence.remote;

import com.company.domain.CurrencyEntity;
import com.company.domain.ExchangeEntity;
import com.company.domain.ExchangedMoneyEntity;
import com.company.persistence.MoneyRepositoryInterface;
import com.company.util.DatabaseSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ExchangedMoneyRepository
        extends GenericRepository<ExchangedMoneyEntity>
        implements MoneyRepositoryInterface<ExchangedMoneyEntity, ExchangeEntity> {

    private static final Class<ExchangedMoneyEntity> entityClass = ExchangedMoneyEntity.class;
    private static ExchangedMoneyRepository instance = null;

    private ExchangedMoneyRepository() {
    }

    public static ExchangedMoneyRepository getInstance() {
        if (instance == null) {
            instance = new ExchangedMoneyRepository();
        }
        return instance;
    }

    public Integer addEx(ExchangedMoneyEntity entity) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();
        Integer id = (Integer) session.save(entity);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    Class<ExchangedMoneyEntity> getEntityClassName() {
        return entityClass;
    }

    @Override
    public List<ExchangedMoneyEntity> getAllMoney(ExchangeEntity ofEntity) {
        return null;
    }

    @Override
    public List<ExchangedMoneyEntity> getAllMoneySorted(ExchangeEntity ofEntity, boolean asc) {
        return null;
    }

    @Override
    public ExchangedMoneyEntity getMoney(ExchangeEntity ofEntity, CurrencyEntity currency) {
        return null;
    }

    @Override
    public void resetAllMoney(ExchangeEntity ofEntity) {

    }
}
