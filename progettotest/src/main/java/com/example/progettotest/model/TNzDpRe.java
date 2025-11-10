package com.example.progettotest.model;
import java.util.Date; // aggiungi questo import


public class TNzDpRe {

    private String id;
    private Date reference_date;
    private String description;
    private Integer amount;
    private String status;

    // Constructors
    public TNzDpRe() {
    }

    public TNzDpRe(String id, Date reference_date, String description, Integer amount, String status) {
        this.id = id;
        this.reference_date = reference_date;
        this.description = description;
        this.amount = amount;
        this.status = status;
    }

    // Getter e Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReference_date() {
        return reference_date;
    }

    public void setReference_date(Date reference_date) {
        this.reference_date = reference_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}