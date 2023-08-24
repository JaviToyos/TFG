package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts;

import java.io.Serializable;
import java.util.List;

public class AccountList implements Serializable {
    private String id;
    private String status;
    private StringStatus agreements;
    private List<String> accounts;
    private String reference;

    public AccountList() {
    }

    public AccountList(String id, String status, StringStatus agreements, List<String> accounts, String reference) {
        this.id = id;
        this.status = status;
        this.agreements = agreements;
        this.accounts = accounts;
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public StringStatus getAgreements() {
        return agreements;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public String getReference() {
        return reference;
    }
}
