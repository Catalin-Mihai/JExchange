package com.company.persistence;

import java.util.ArrayList;

import com.company.domain.Currency;
import com.company.domain.Money;
import com.company.exceptions.DuplicateCurrencyException;

public class MoneyRepository implements GenericCurrencyRepository<Money> {

    /**
     * Depozitul de bani de diferite valute
     * Mereu sortat
     */
    ArrayList<Money> repo;

    public MoneyRepository(){
        repo = new ArrayList<>();
    }

    MoneyRepository(ArrayList<Money> repo){
        this.repo = repo;
    }

    @Override
    public void add(Money entity) throws DuplicateCurrencyException {
        //Adaugam bani de o noua valuta in repo
        for(Money money: repo){
            if(money.equalsCurrency(entity)){
                throw new DuplicateCurrencyException("Aceasta moneda exista deja!");
            }
        }
        repo.add(entity);
    }

    //Exceptie in caz de index prost
    @Override
    public Money get(int index) throws IndexOutOfBoundsException{
        return repo.get(index);
    }

    public int getAmountByCurrency(Currency currency){
        for(Money money: repo){
            if(money.equalsCurrency(currency)){
                return money.getAmount();
            }
        }
        return -1;
    }

    @Override
    public int update(Money entity) {
        for (int i = 0; i < repo.size(); i++) {
            Money slot = repo.get(i);
            if(slot.equalsCurrency(entity)) {
                repo.set(i, entity);
                return 1;
            }
        }
        return 0;
    }

    public boolean exists(Money entity){
        return repo.contains(entity);
    }

    @Override
    public void delete(Money entity) {
        repo.remove(entity);
    }

    @Override
    public int getSize() {
        return repo.size();
    }

    public void modifyAmount(int index, int amount){
        repo.get(index).setAmount(amount);
    }

    public ArrayList<Money> getRepository(){
        return repo;
    }
}
