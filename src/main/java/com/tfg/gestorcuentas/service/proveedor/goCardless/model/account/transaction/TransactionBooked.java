package com.tfg.gestorcuentas.service.proveedor.goCardless.model.account.transaction;

import java.io.Serializable;
import java.util.Date;

public class TransactionBooked implements Serializable {
    private String transactionId;
    private DebtorAccount debtorName;
    private TransactionAmount transactionAmount;
    private Date bookingDate;
    private Date valueDate;
    private String remittanceInformationUnstructured;


    public TransactionBooked() {
    }

    public TransactionBooked(String transactionId, DebtorAccount debtorName, TransactionAmount transactionAmount, Date bookingDate, Date valueDate, String remittanceInformationUnstructured) {
        this.transactionId = transactionId;
        this.debtorName = debtorName;
        this.transactionAmount = transactionAmount;
        this.bookingDate = bookingDate;
        this.valueDate = valueDate;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public DebtorAccount getDebtorName() {
        return debtorName;
    }

    public TransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }
}
