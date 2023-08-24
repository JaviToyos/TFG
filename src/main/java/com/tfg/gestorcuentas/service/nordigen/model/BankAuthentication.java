package com.tfg.gestorcuentas.service.nordigen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

public class BankAuthentication implements Serializable {
    private String id;
    private String redirect;
    @JsonIgnoreProperties
    private Status status;
    private String agreements;
    private List<String> accounts;
    private String reference;
    private String user_language;
    private String link;

    public BankAuthentication() {
    }

    public BankAuthentication(String id, String redirect, Status status, String agreements, List<String> accounts, String reference, String user_language, String link) {
        this.id = id;
        this.redirect = redirect;
        this.status = status;
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

    public Status getStatus() {
        return status;
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

    private class Status {
        private String description;

        public Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
