package com.company.persistence.remote;

import com.company.domain.OfficeEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Function;

public class OfficeRepository extends GenericRepository<OfficeEntity> {

    private static final Class<OfficeEntity> entityClass = OfficeEntity.class;
    private static OfficeRepository instance = null;

    private OfficeRepository() {
    }

    public static OfficeRepository getInstance() {
        if (instance == null) {
            instance = new OfficeRepository();
        }
        return instance;
    }

    @Override
    Class<OfficeEntity> getEntityClassName() {
        return entityClass;
    }

    public OfficeEntity getOffice(String officeName) {
        Function<Session, OfficeEntity> f = session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OfficeEntity> cr = cb.createQuery(entityClass);
            Root<OfficeEntity> root = cr.from(entityClass);
            cr.select(root).where(cb.equal(root.get("name"), officeName));
            Query<OfficeEntity> query = session.createQuery(cr);
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        };
        return AbstractRepository.transactionExecutor(f);
    }


}
