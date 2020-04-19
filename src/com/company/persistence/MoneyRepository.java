package com.company.persistence;

import com.company.domain.Currency;
import com.company.domain.Money;
import com.company.exceptions.InvalidMoneyException;

import java.util.ArrayList;

public class MoneyRepository extends GenericRepository<Money> {

    /**
     * Depozitul de bani de diferite valute
     */

    @Override
    public boolean exists(Money entity) {
        // Exista deja valuta (nu ne raportam la cantitate/amount)
        for (Money money : repo) {
            if (money.equalsCurrency(entity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
        return repo.size();
    }

    public Money getByCurrency(Currency currency) {
        for (Money money : repo) {
            if (money.equalsCurrency(currency)) {
                return money;
            }
        }
        return null;
    }

    public Float getAmountByCurrency(Currency currency) {
        for (Money money : repo) {
            if (money.equalsCurrency(currency)) {
                return money.getAmount();
            }
        }
        throw new InvalidMoneyException("Nu exista bani in aceasta valuta");
    }

    public void modifyAmount(int index, Float amount) {
        repo.get(index).setAmount(amount);
    }

    public ArrayList<Money> getRepository() {
        return repo;
    }
}
