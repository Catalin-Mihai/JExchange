package com.company.persistence.local;

import com.company.domain.LogEntity;

public class LogRepository extends GenericArrayListRepository<LogEntity> {

    private static LogRepository instance;

    public static LogRepository getInstance() {
        if(instance == null){
            instance = new LogRepository();
        }
        return instance;
    }

    private LogRepository() {}

    @Override
    public LogEntity get(int id) {
        for(LogEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }
}
