package com.tfg.gestorcuentas.service.proveedor.goCardless.model.bank;

import java.io.Serializable;
import java.util.Objects;

public class BankList implements Serializable {
    private Bank availableBanks;

    public BankList() {
    }

    public BankList(Bank availableBanks) {
        this.availableBanks = availableBanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankList bankList = (BankList) o;
        return Objects.equals(availableBanks, bankList.availableBanks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableBanks);
    }
}
