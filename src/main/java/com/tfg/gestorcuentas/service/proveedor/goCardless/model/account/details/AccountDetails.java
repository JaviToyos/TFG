package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.details;

import java.io.Serializable;

public class AccountDetails implements Serializable {
    private String resourceId;
    private String iban;
    private String currency;
    private String ownerName;
    private String name;
    private String product;
    private String cashAccountType;

    public AccountDetails() {
    }

    public AccountDetails(String resourceId, String iban, String currency, String ownerName, String name, String product, String cashAccountType) {
        this.resourceId = resourceId;
        this.iban = iban;
        this.currency = currency;
        this.ownerName = ownerName;
        this.name = name;
        this.product = product;
        this.cashAccountType = cashAccountType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public String getCashAccountType() {
        return cashAccountType;
    }
}
