package com.company.persistence;

import java.util.ArrayList;

public interface GenericArrayRepositoryInterface<T> {

    boolean add(T entity);

    T get(int id);

    int indexOf(T entity);

    int update(T entity);

    void delete(T entity);

    int getSize();

    boolean exists(T entity);

    ArrayList<T> getRepo();
}
