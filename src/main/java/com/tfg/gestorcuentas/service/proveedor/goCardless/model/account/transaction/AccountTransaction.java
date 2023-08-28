package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;

public class AccountTransaction implements Serializable {
    private TransactionBooked booked;
    private TransactionPending pending;


    public AccountTransaction() {
    }

    public AccountTransaction(TransactionBooked booked, TransactionPending transactionPending) {
        this.booked = booked;
        this.pending = transactionPending;
    }

    public TransactionBooked getTransactionBooked() {
        return booked;
    }

    public TransactionPending getTransactionPending() {
        return pending;
    }
}
