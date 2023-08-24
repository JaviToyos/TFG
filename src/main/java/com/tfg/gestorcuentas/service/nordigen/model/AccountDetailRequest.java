package com.tfg.gestorcuentas.service.nordigen.model;

public class AccountDetailRequest {
    private String token;
    private String accountId;

    public AccountDetailRequest() {
    }

    public AccountDetailRequest(String token, String accountId) {
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
