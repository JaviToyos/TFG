package com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank;

import com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.userAccounts.StringStatus;

import java.io.Serializable;
import java.util.List;

public class BankLink implements Serializable {
    private String id;
    private String redirect;
    private StringStatus stringStatus;
    private String agreements;
    private List<String> accounts;
    private String reference;
    private String user_language;
    private String link;

    public BankLink() {
    }

    public BankLink(String id, String redirect, StringStatus stringStatus, String agreements, List<String> accounts, String reference, String user_language, String link) {
        this.id = id;
        this.redirect = redirect;
        this.stringStatus = stringStatus;
        this.agreements = agreements;
        this.accounts = accounts;
        this.reference = reference;
        this.user_language = user_language;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getRedirect() {
        return redirect;
    }

    public StringStatus getStatus() {
        return stringStatus;
    }

    public String getAgreements() {
        return agreements;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public String getReference() {
        return reference;
    }

    public String getUser_language() {
        return user_language;
    }

    public String getLink() {
        return link;
    }


}
