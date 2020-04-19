package com.company.persistence;

import java.util.ArrayList;

public abstract class GenericRepository<T> implements GenericArrayRepositoryInterface<T> {

    ArrayList<T> repo = new ArrayList<>();

    @Override
    public boolean add(T entity) {
        if (!exists(entity)){
            repo.add(entity);
            return true;
        }
        return false;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        return repo.get(index);
    }



    @Override
    public int update(T entity) {
        for (int i = 0; i < repo.size(); i++) {
            if (repo.get(i).equals(entity)) {
                repo.set(i, entity);
                return 1;
            }
        }
        return 0;
    }

    @Override
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

    @Override
    public boolean exists(T entity){
        return repo.contains(entity);
    }

    @Override
    public ArrayList<T> getRepo() {
        return repo;
    }
}
