package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;
import java.util.Date;

public class TransactionPending implements Serializable {

    private TransactionAmount transactionAmount;
    private Date valueDate;
    private String remittanceInformationUnstructured;


    public TransactionPending() {
    }

    public TransactionPending(TransactionAmount transactionAmount, Date valueDate, String remittanceInformationUnstructured) {
        this.transactionAmount = transactionAmount;
        this.valueDate = valueDate;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public TransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }
}
