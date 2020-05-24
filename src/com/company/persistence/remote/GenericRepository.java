package com.company.persistence.remote;

import com.company.persistence.GenericRepositoryInterface;

import java.util.List;

public abstract class GenericRepository<T> implements GenericRepositoryInterface<T> {

    protected AbstractRepository globalManager = AbstractRepository.getInstance();

    abstract Class<T> getEntityClassName();

    @Override
    public void add(T entity) {
        globalManager.add(entity);
    }

    @Override
    public T get(int id) {
        return globalManager.get(getEntityClassName(), id);
    }

    @Override
    public void update(T entity) {
        globalManager.update(entity);
    }

    @Override
    public void delete(T entity) {
        globalManager.delete(entity);
    }

    @Override
    public int getSize() {
        return globalManager.getSize(getEntityClassName());
    }

    public List<T> getAll() {
        return globalManager.getAll(getEntityClassName());
    }
}
