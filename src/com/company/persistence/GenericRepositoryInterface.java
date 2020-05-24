package com.company.persistence;

public interface GenericRepositoryInterface<T> {

    void add(T entity);

    T get(int id);

    void update(T entity);

    void delete(T entity);

    int getSize();

}