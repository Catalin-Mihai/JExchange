package com.company.persistence.remote;

import com.company.domain.CurrencyEntity;
import com.company.domain.ExchangeRateEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Function;

public class ExchangeRateRepository extends GenericRepository<ExchangeRateEntity> {

    private static final Class<ExchangeRateEntity> ENTITY_CLASS = ExchangeRateEntity.class;
    private static ExchangeRateRepository instance = null;

    private ExchangeRateRepository() {
    }

    public static ExchangeRateRepository getInstance() {
        if (instance == null) {
            instance = new ExchangeRateRepository();
        }
        return instance;
    }

    @Override
    Class<ExchangeRateEntity> getEntityClassName() {
        return ENTITY_CLASS;
    }

    public ExchangeRateEntity getExchangeRate(CurrencyEntity from, CurrencyEntity to) {
        Function<Session, ExchangeRateEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ExchangeRateEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<ExchangeRateEntity> root = cr.from(ENTITY_CLASS);
            cr.select(root).where(cb.equal(root.get("fromCurrency"), from),
                    cb.equal(root.get("toCurrency"), to));
            Query<ExchangeRateEntity> query = session.createQuery(cr);
//            query.setMaxResults(1);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }

    public ExchangeRateEntity getExchangeRate(CurrencyEntity from, CurrencyEntity to, Double rate) {
        Function<Session, ExchangeRateEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ExchangeRateEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<ExchangeRateEntity> root = cr.from(ENTITY_CLASS);
            cr.select(root).where(cb.equal(root.get("fromCurrency"), from),
                    cb.equal(root.get("toCurrency"), to),
                    cb.equal(root.get("rate"), rate));
            Query<ExchangeRateEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }
}
