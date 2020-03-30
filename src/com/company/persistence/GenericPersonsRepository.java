package com.company.persistence;

import com.company.exceptions.DuplicateClientException;

public interface GenericPersonsRepository<T> {
    void add(T entity) throws DuplicateClientException;

    T get(int id);

    void update(T entity);

    void remove(T entity);

    int getSize();
}
