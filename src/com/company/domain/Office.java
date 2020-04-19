package com.company.domain;

public class Office {

    private static Office instance = new Office();
    /**
     * Defineste casa de schimb valutar
     * Exista doar o singura casa de acest tip
     * Clasa Singleton cu initializare rapida (Eager initialization)
     */

    private String officeName = "Exchange Office";
    private String address = "Bld. Nicolaie Balcescu, Nr. 65";

    private Office() {
    }

    public static Office getInstance() {
        return instance;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
