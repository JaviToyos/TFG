package com.tfg.gestorcuentas.service.nordigen.model;

import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.Bank;

public class BankLinkRequest {
    private String token;
    private Bank bank;

    public BankLinkRequest() {
    }

    public BankLinkRequest(String token, Bank bank) {
        this.token = token;
        this.bank = bank;
    }

    public String getToken() {
        return token;
    }

    public Bank getBank() {
        return bank;
    }
}
