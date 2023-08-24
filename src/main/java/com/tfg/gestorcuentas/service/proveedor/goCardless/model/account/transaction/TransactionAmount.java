package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;

public class TransactionAmount implements Serializable {
    private String currency;
    private Double amount;


    public TransactionAmount() {
    }

    public TransactionAmount(String currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }
}
