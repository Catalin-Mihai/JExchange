package com.company.domain;

import com.company.persistence.MoneyRepository;

import java.util.Objects;

public class Client {

    /**
     * Aici repository-ul nu trebuie sa fie static. Fiecare client are repository-ul lui
     */
    private MoneyRepository moneyRepository = new MoneyRepository();

    private String firstName;
    private String lastName;

    public Client(String fname, String lname){
        this.firstName = fname;
        this.lastName = lname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addMoney(Money money){

    }

    public MoneyRepository getMoneyRepository() {
        return moneyRepository;
    }

    public void setMoneyRepository(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
