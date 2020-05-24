package com.company.persistence.local;

import com.company.domain.ClientMoneyEntity;
import com.company.domain.CurrencyEntity;
import com.company.domain.OfficeEntity;
import com.company.domain.OfficeMoneyEntity;
import com.company.persistence.MoneyRepositoryInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OfficeMoneyRepository
        extends GenericArrayListRepository<OfficeMoneyEntity>
        implements MoneyRepositoryInterface<OfficeMoneyEntity, OfficeEntity> {

    public static OfficeMoneyRepository instance;

    public static OfficeMoneyRepository getInstance() {
        if(instance == null){
            instance = new OfficeMoneyRepository();
        }
        return instance;
    }

    private OfficeMoneyRepository() {}

    @Override
    public OfficeMoneyEntity get(int id) {
        for(OfficeMoneyEntity entity: repo){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<OfficeMoneyEntity> getAllMoney(OfficeEntity ofEntity) {
        ArrayList<OfficeMoneyEntity> lista = new ArrayList<>();
        for (OfficeMoneyEntity entity : repo) {
            if(entity.getId() == ofEntity.getId())
                lista.add(entity);
        }
        return lista;
    }

    @Override
    public List<OfficeMoneyEntity> getAllMoneySorted(OfficeEntity ofEntity, boolean asc) {
        ArrayList<OfficeMoneyEntity> money = (ArrayList<OfficeMoneyEntity>) getAllMoney(ofEntity);
        money.sort(new Comparator<OfficeMoneyEntity>() {
            @Override
            public int compare(OfficeMoneyEntity o1, OfficeMoneyEntity o2) {
                if(asc)
                    return o1.getAmount().compareTo(o2.getAmount());
                else
                    return (-1)*o1.getAmount().compareTo(o2.getAmount());
            }
        });
        return money;
    }

    @Override
    public OfficeMoneyEntity getMoney(OfficeEntity ofEntity, CurrencyEntity currency) {
        for(OfficeMoneyEntity entity: repo){
            if(entity.getCurrency().getId() == currency.getId()
                    && ofEntity.getId() == entity.getId()){
                return entity;
            }
        }
        return null;
    }

    @Override
    public void resetAllMoney(OfficeEntity ofEntity) {
        for (OfficeMoneyEntity entity : repo) {
            if(entity.getId() == ofEntity.getId())
                entity.setAmount(0d);
        }
    }
}
