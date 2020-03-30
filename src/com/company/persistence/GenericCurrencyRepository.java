package com.company.persistence;

import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;

public interface GenericCurrencyRepository<T> {

    void add(T entity) throws DuplicateCurrencyException, DuplicateClientException;

    T get(int id);

    int update(T entity);

    void delete(T entity);

    int getSize();
}
