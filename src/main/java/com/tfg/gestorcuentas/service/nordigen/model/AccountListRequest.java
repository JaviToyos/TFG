package com.tfg.gestorcuentas.service.nordigen.model;

import com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank.BankLink;

public class AccountListRequest {
    private String token;
    private BankLink bankLink;

    public AccountListRequest() {
    }

    public AccountListRequest(String token, BankLink bankLink) {
        this.token = token;
        this.bankLink = bankLink;
    }

    public String getToken() {
        return token;
    }


    public BankLink getBankLink() {
        return bankLink;
    }
}
