package com.company.persistence.local;

import com.company.domain.ClientMoneyEntity;
import com.company.domain.OfficeEntity;

public class OfficeRepository extends GenericArrayListRepository<OfficeEntity>{

    public static OfficeRepository instance;

    public static OfficeRepository getInstance() {
        if(instance == null){
            instance = new OfficeRepository();
        }
        return instance;
    }

    private OfficeRepository() {}

    @Override
    public OfficeEntity get(int id) {
        for(OfficeEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }

    public OfficeEntity getOffice(String name){
        for(OfficeEntity entity: repo){
            if(entity.getName().equals(name)){
                return entity;
            }
        }
        return null;
    }
}
