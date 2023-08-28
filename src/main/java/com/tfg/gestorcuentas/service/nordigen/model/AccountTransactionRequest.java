package com.tfg.gestorcuentas.service.nordigen.model;

public class AccountTransactionRequest {
    private String token;
    private String accountId;

    public AccountTransactionRequest() {
    }

    public AccountTransactionRequest(String token, String accountId) {
        this.token = token;
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public String getAccountId() {
        return accountId;
    }
}
