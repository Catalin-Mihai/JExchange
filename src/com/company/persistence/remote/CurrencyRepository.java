package com.company.persistence.remote;

import com.company.domain.CurrencyEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Function;

public class CurrencyRepository extends GenericRepository<CurrencyEntity> {
    private static final Class<CurrencyEntity> entityClass = CurrencyEntity.class;
    private static CurrencyRepository instance = null;

    private CurrencyRepository() {
    }

    public static CurrencyRepository getInstance() {
        if (instance == null) {
            instance = new CurrencyRepository();
        }
        return instance;
    }

    @Override
    Class<CurrencyEntity> getEntityClassName() {
        return entityClass;
    }

    public CurrencyEntity getCurrency(String currencyName) {
        Function<Session, CurrencyEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CurrencyEntity> cr = cb.createQuery(entityClass);
            Root<CurrencyEntity> root = cr.from(entityClass);
            cr.select(root)
                    .where(cb.equal(root.get("name"), currencyName));
            Query<CurrencyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }
}
