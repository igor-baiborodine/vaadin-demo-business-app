package com.kiroule.vaadin.businessapp.backend;

import java.time.LocalDate;

public class BankAccount {

    private Long id;
    private String bank;
    private String account;
    private String owner;
    private Double availability;
    private LocalDate updated;

    public BankAccount(Long id, String bank, String account, String company,
            Double availability, LocalDate updated) {
        this.id = id;
        this.bank = bank;
        this.account = account;
        this.owner = company;
        this.availability = availability;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    public String getOwner() {
        return owner;
    }

    public Double getAvailability() {
        return availability;
    }

    public LocalDate getUpdated() {
        return updated;
    }
}
