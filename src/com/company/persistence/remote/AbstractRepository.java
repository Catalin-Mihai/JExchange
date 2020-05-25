package com.company.persistence.remote;

import com.company.util.DatabaseSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class AbstractRepository {

    private static AbstractRepository instance = null;

    private AbstractRepository() {
    }

    public static AbstractRepository getInstance() {
        if (instance == null) {
            instance = new AbstractRepository();
        }
        return instance;
    }

    public static <T> T transactionExecutor(Function<Session, T> toRun) {
        Session session = null;
        T result = null;
        try {
            session = DatabaseSession.getSession();
            final Transaction tx = session.beginTransaction();
            result = toRun.apply(session);
            session.flush();
            tx.commit();
            return result;
        } catch (HibernateException he) {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            tx.rollback();
        } finally {

            Objects.requireNonNull(session).close();
        }
        return result;
    }

    public <T> void add(final T entity) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
    }

    public <T> T get(final Class<T> entityClass, int id) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();
        T entity = session.get(entityClass, id);
        tx.commit();
        session.close();
        return entity;
    }

    public <T> void update(final T entity) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();
        session.update(entity);
        tx.commit();
        session.close();
    }

    public <T> void delete(T entity) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(entity);
        tx.commit();
        session.close();
    }

    public <T> List<T> getAll(final Class<T> entityClass) {
        Session session = DatabaseSession.getSession();
        Transaction tx = session.beginTransaction();

        CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.from(entityClass);

        List<T> resultList = session.createQuery(criteriaQuery).getResultList();
        tx.commit();
        session.close();
        return resultList;
    }

    public <T> int getSize(final Class<T> entityClass) {
        return getAll(entityClass).size();
    }
}
