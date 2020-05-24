package com.company.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "currencies", schema = "pao")
public class CurrencyEntity {
    private int id;
    private String name;
    private String symbol;
    private String country;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue( strategy = GenerationType.AUTO )
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "symbol", nullable = true, length = 50)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 50)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity that = (CurrencyEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, country);
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
