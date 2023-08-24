package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;

public class AccountTransaction implements Serializable {
    private TransactionBooked transactionBooked;
    private TransactionPending transactionPending;


    public AccountTransaction() {
    }

    public AccountTransaction(TransactionBooked transactionBooked, TransactionPending transactionPending) {
        this.transactionBooked = transactionBooked;
        this.transactionPending = transactionPending;
    }

    public TransactionBooked getTransactionBooked() {
        return transactionBooked;
    }

    public TransactionPending getTransactionPending() {
        return transactionPending;
    }
}
