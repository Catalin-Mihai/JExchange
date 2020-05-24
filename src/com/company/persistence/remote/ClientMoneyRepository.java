package com.company.persistence.remote;

import com.company.domain.ClientEntity;
import com.company.domain.ClientMoneyEntity;
import com.company.domain.CurrencyEntity;
import com.company.persistence.MoneyRepositoryInterface;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

public class ClientMoneyRepository extends GenericRepository<ClientMoneyEntity>
        implements MoneyRepositoryInterface<ClientMoneyEntity, ClientEntity> {

    private static final Class<ClientMoneyEntity> entityClass = ClientMoneyEntity.class;
    private static ClientMoneyRepository instance = null;

    private ClientMoneyRepository() {
    }

    public static ClientMoneyRepository getInstance() {
        if (instance == null) {
            instance = new ClientMoneyRepository();
        }
        return instance;
    }

    @Override
    Class<ClientMoneyEntity> getEntityClassName() {
        return entityClass;
    }

    @Override
    public List<ClientMoneyEntity> getAllMoney(ClientEntity ofEntity) {
        System.out.println(ofEntity);
        Function<Session, List<ClientMoneyEntity>> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientMoneyEntity> cr = cb.createQuery(entityClass);
            Root<ClientMoneyEntity> root = cr.from(entityClass);
            cr.select(root)
                    .where(cb.equal(root.get("client"), ofEntity));
            Query<ClientMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList();
        };
        return AbstractRepository.transactionExecutor(f);
    }

    @Override
    public ClientMoneyEntity getMoney(ClientEntity ofEntity, CurrencyEntity currency) {
        Function<Session, ClientMoneyEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientMoneyEntity> cr = cb.createQuery(entityClass);
            Root<ClientMoneyEntity> root = cr.from(entityClass);
            cr.select(root).where(cb.equal(root.get("client"), ofEntity),
                    cb.equal(root.get("currency"), currency));
            Query<ClientMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }

    @Override
    public void resetAllMoney(ClientEntity ofEntity) {
        Function<Session, ClientMoneyEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<ClientMoneyEntity> criteriaUpdate = cb.createCriteriaUpdate(entityClass);
            Root<ClientMoneyEntity> root = criteriaUpdate.from(entityClass);
            criteriaUpdate.set("amount", 0d);
            criteriaUpdate.where(cb.equal(root.get("client"), ofEntity));
            session.createQuery(criteriaUpdate).executeUpdate();
            return null;
        };
        AbstractRepository.transactionExecutor(f);
    }

    @Override
    public List<ClientMoneyEntity> getAllMoneySorted(ClientEntity ofEntity, boolean asc) {
        Function<Session, List<ClientMoneyEntity>> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientMoneyEntity> cr = cb.createQuery(entityClass);
            Root<ClientMoneyEntity> root = cr.from(entityClass);
            if (asc)
                cr.select(root).orderBy(cb.asc(root.get("amount")));
            else
                cr.select(root).orderBy(cb.desc(root.get("amount")));
            cr.where(cb.equal(root.get("client"), ofEntity));
            Query<ClientMoneyEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList();
        };
        return AbstractRepository.transactionExecutor(f);
    }
}
