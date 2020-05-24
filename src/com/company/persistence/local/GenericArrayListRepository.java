package com.company.persistence.local;

import com.company.persistence.GenericRepositoryInterface;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;

public abstract class GenericArrayListRepository<T> implements GenericRepositoryInterface<T> {

    ArrayList<T> repo = new ArrayList<>();

    @Override
    public void add(T entity) {
        if (!exists(entity)){
            repo.add(entity);
        }
    }

    @Override
    public void update(T entity) {
        for (int i = 0; i < repo.size(); i++) {
            if (repo.get(i).equals(entity)) {
                repo.set(i, entity);
            }
        }
    }

    public int indexOf(T entity) {
        for (int i = 0; i < repo.size(); i++) {
            if (repo.get(i).equals(entity)) {
                return i;
            }
        }
        return -1;
    }

    //Nu avem duplicate
    @Override
    public void delete(T entity) {
        repo.remove(entity);
    }

    @Override
    public int getSize() {
        return repo.size();
    }

    public boolean exists(T entity){
        return repo.contains(entity);
    }

    public Collection<T> getAll() {
        return repo;
    }
}
