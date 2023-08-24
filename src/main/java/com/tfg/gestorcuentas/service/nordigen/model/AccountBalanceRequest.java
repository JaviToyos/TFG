package com.tfg.gestorcuentas.service.nordigen.model;

public class AccountBalanceRequest {

    private String token;
    private String accountId;

    public AccountBalanceRequest() {
    }

    public AccountBalanceRequest(String token, String accountId) {
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
