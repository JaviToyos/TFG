package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.balance;

import java.io.Serializable;
import java.util.Date;

public class AccountBalance implements Serializable {
    private BalanceAmount balanceAmount;
    private String balanceType;
    private Date referenceDate;

    public AccountBalance() {
    }

    public AccountBalance(BalanceAmount balanceAmount, String balanceType, Date referenceDate) {
        this.balanceAmount = balanceAmount;
        this.balanceType = balanceType;
        this.referenceDate = referenceDate;
    }

    public BalanceAmount getBalanceAmount() {
        return balanceAmount;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }
}
