package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;

public class DebtorAccount implements Serializable {
    private String iban;


    public DebtorAccount() {
    }

    public DebtorAccount(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }
}
