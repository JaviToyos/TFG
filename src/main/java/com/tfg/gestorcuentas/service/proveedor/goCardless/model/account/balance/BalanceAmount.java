package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance;

import java.io.Serializable;

public class BalanceAmount implements Serializable {
    private Double amount;
    private String currency;

    public BalanceAmount() {
    }

    public BalanceAmount(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
