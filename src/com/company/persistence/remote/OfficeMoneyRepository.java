package com.company.persistence.remote;

import com.company.domain.CurrencyEntity;
import com.company.domain.OfficeEntity;
import com.company.domain.OfficeMoneyEntity;
import com.company.persistence.MoneyRepositoryInterface;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

public class OfficeMoneyRepository
        extends GenericRepository<OfficeMoneyEntity>
        implements MoneyRepositoryInterface<OfficeMoneyEntity, OfficeEntity> {

    private static final Class<OfficeMoneyEntity> ENTITY_CLASS = OfficeMoneyEntity.class;
    private static OfficeMoneyRepository instance = null;

    private OfficeMoneyRepository() {
    }

    public static OfficeMoneyRepository getInstance() {
        if (instance == null) {
            instance = new OfficeMoneyRepository();
        }
        return instance;
    }

    @Override
    Class<OfficeMoneyEntity> getEntityClassName() {
        return ENTITY_CLASS;
    }

    @Override
    public List<OfficeMoneyEntity> getAllMoney(OfficeEntity ofEntity) {
        System.out.println(ofEntity);
        Function<Session, List<OfficeMoneyEntity>> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OfficeMoneyEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<OfficeMoneyEntity> root = cr.from(ENTITY_CLASS);
            cr.select(root)
                    .where(cb.equal(root.get("office"), ofEntity));
            Query<OfficeMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList();
        };
        return AbstractRepository.transactionExecutor(f);
    }

    @Override
    public List<OfficeMoneyEntity> getAllMoneySorted(OfficeEntity ofEntity, boolean asc) {
        Function<Session, List<OfficeMoneyEntity>> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OfficeMoneyEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<OfficeMoneyEntity> root = cr.from(ENTITY_CLASS);
            if (asc)
                cr.select(root).orderBy(cb.asc(root.get("amount")));
            else
                cr.select(root).orderBy(cb.desc(root.get("amount")));
            cr.where(cb.equal(root.get("office"), ofEntity));
            Query<OfficeMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList();
        };
        return AbstractRepository.transactionExecutor(f);
    }

    @Override
    public OfficeMoneyEntity getMoney(OfficeEntity ofEntity, CurrencyEntity currency) {
        Function<Session, OfficeMoneyEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OfficeMoneyEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<OfficeMoneyEntity> root = cr.from(ENTITY_CLASS);
            cr.select(root).where(cb.equal(root.get("office"), ofEntity),
                    cb.equal(root.get("currency"), currency));
            Query<OfficeMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }

    @Override
    public void resetAllMoney(OfficeEntity ofEntity) {
        Function<Session, OfficeMoneyEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<OfficeMoneyEntity> criteriaUpdate = cb.createCriteriaUpdate(ENTITY_CLASS);
            Root<OfficeMoneyEntity> root = criteriaUpdate.from(ENTITY_CLASS);
            criteriaUpdate.set("amount", 0d);
            criteriaUpdate.where(cb.equal(root.get("office"), ofEntity));
            session.createQuery(criteriaUpdate).executeUpdate();
            return null;
        };
        AbstractRepository.transactionExecutor(f);
    }
}
