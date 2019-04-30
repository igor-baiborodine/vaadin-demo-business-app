package com.kiroule.vaadin.bakeryapp.backend;

import java.time.LocalDate;

public class Transaction {

    public enum Status {
        PENDING("Pending"), PROCESSED("Processed");

        private String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Long id;
    private Status status;
    private String company;
    private String iban;
    private Double amount;
    private boolean attachment;
    private LocalDate date;

    public Transaction(Long id, Status status, String company, String iban,
            Double amount, boolean attachment, LocalDate date) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.iban = iban;
        this.amount = amount;
        this.attachment = attachment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getCompany() {
        return company;
    }

    public String getIBAN() {
        return iban;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean hasAttachment() {
        return attachment;
    }

    public LocalDate getDate() {
        return date;
    }
}
