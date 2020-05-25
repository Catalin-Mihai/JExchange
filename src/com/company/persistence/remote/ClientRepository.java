package com.company.persistence.remote;

import com.company.domain.ClientEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Function;

public class ClientRepository extends GenericRepository<ClientEntity> {

    private static final Class<ClientEntity> ENTITY_CLASS = ClientEntity.class;
    private static ClientRepository instance = null;

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    @Override
    Class<ClientEntity> getEntityClassName() {
        return ENTITY_CLASS;
    }

    public ClientEntity getClient(String firstName) {
        Function<Session, ClientEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientEntity> cr = cb.createQuery(ENTITY_CLASS);
            Root<ClientEntity> root = cr.from(ENTITY_CLASS);
            cr.select(root).where(cb.equal(root.get("firstName"), firstName));
            Query<ClientEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }


}

