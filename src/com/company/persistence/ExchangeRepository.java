package com.company.persistence;

import com.company.domain.Exchange;

public class ExchangeRepository extends GenericRepository<Exchange> {

    public Exchange getByID(int id) {
        for (Exchange exchange : repo) {
            if (exchange.getExchangeID().equals(id)) {
                return exchange;
            }
        }
        return null;
    }
}
