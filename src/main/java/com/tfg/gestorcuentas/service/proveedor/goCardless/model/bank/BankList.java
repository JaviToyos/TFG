package com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank;

import java.io.Serializable;

public class BankList implements Serializable {
    private Bank availableBanks;

    public BankList() {
    }

    public BankList(Bank availableBanks) {
        this.availableBanks = availableBanks;
    }

    public Bank getAvailableBanks() {
        return availableBanks;
    }

    public void setAvailableBanks(Bank availableBanks) {
        this.availableBanks = availableBanks;
    }
}
